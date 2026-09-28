package com.dtn2603.service.impl;

import com.dtn2603.entity.Account;
import com.dtn2603.entity.Department;
import com.dtn2603.entity.Position;
import com.dtn2603.entity.enums.Gender;
import com.dtn2603.exception.ResourceNotFoundException;
import com.dtn2603.repository.IAccountRepository;
import com.dtn2603.service.IAccountService;
import com.dtn2603.service.IDepartmentService;
import com.dtn2603.service.IPositionService;
import com.dtn2603.utils.ValidationUtils;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private final int MIN_LENGTH = 6;
    private final int MAX_LENGTH = 100;
    private final IAccountRepository accountRepository;
    private final IDepartmentService departmentService;
    private final IPositionService positionService;

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public boolean addAccount(Account account) {
        String message = validateAccount(account);
        if(Objects.nonNull(message)) {
            throw new IllegalArgumentException(message);
        }
        return accountRepository.save(account);
    }

    @Override
    public boolean updateAccount(Account account) {
         if (Objects.isNull(account)) {
             throw new IllegalArgumentException("Account can't be null");
         }

        if (!ValidationUtils.isValidLength(account.getUsername(), MIN_LENGTH, MAX_LENGTH)) {
            throw new IllegalArgumentException(String.format("Account username length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (account.getAccountId() <= 0) {
            throw new IllegalArgumentException("Account id must be positive");
        }

        if (Objects.isNull(accountRepository.findById(account.getAccountId()))) {
            throw new ResourceNotFoundException("Account id not found");
        }

        Account existedAccount = accountRepository.findByUsername(account.getUsername());
        if (Objects.nonNull(existedAccount)
                && !Objects.equals(existedAccount.getAccountId(), account.getAccountId())) {
            throw new IllegalArgumentException("Account username already exist");
        }
        return accountRepository.update(account);
    }

    @Override
    public boolean deleteAccountById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Account id must be positive");
        }

        if (Objects.isNull(accountRepository.findById(id))) {
            throw new ResourceNotFoundException("Account id not found");
        }
        return accountRepository.deleteById(id);
    }

    @Override
    public int getRequiredFieldCount() {
        return 6;
    }

    @Override
    public String getErrorLogFileName() {
        return "insert_account_error_log.csv";
    }

    @Override
    public Account parseFields(List<String> fields) {
        Account account = new Account();
        account.setEmail(fields.get(0).trim());
        account.setUsername(fields.get(1).trim());
        account.setFullName(fields.get(2).trim());
        String departmentId = fields.get(3).trim();
        account.setDepartment(departmentId.isEmpty()
                ? null : new Department(parsePositiveId("Department id", departmentId)));
        String positionId = fields.get(4).trim();
        account.setPosition(positionId.isEmpty()
                ? null : new Position(parsePositiveId("Position id", positionId)));
        String gender = fields.get(5).trim();
        account.setGender("M".equals(gender) || "F".equals(gender) ? Gender.valueOf(gender) : Gender.U);
        return account;
    }

    private int parsePositiveId(String label, String value) {
        try {
            int id = Integer.parseInt(value);
            if (id <= 0) {
                throw new IllegalArgumentException(label + " must be positive");
            }
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(label + " must be a number: " + value);
        }
    }

    @Override
    public Map<String, Set<String>> loadContext(List<Account> items) {
        List<String> usernames = items.stream()
                .map(Account::getUsername)
                .collect(Collectors.toList());
        List<String> emails = items.stream()
                .map(Account::getEmail)
                .collect(Collectors.toList());
        List<Integer> departmentIds = items.stream()
                .map(Account::getDepartment)
                .filter(Objects::nonNull)
                .map(Department::getDepartmentId)
                .collect(Collectors.toList());
        List<Integer> positionIds = items.stream()
                .map(Account::getPosition)
                .filter(Objects::nonNull)
                .map(Position::getPositionId)
                .collect(Collectors.toList());

        Map<String, Set<String>> context = new HashMap<>();
        context.put("username", lowerCase(accountRepository.findExistingUsernames(usernames)));
        context.put("email", lowerCase(accountRepository.findExistingEmails(emails)));
        context.put("departmentId", toStringSet(departmentService.findExistingDepartmentIds(departmentIds)));
        context.put("positionId", toStringSet(positionService.findExistingPositionIds(positionIds)));
        return context;
    }

    private Set<String> lowerCase(Collection<String> values) {
        return values.stream()
                .map(value -> value)
                .collect(Collectors.toSet());
    }

    private Set<String> toStringSet(Collection<Integer> values) {
        return values.stream()
                .map(String::valueOf)
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String, String> extractKeys(Account item) {
        Map<String, String> keys = new HashMap<>();
        keys.put("username", item.getUsername());
        keys.put("email", item.getEmail());
        return keys;
    }

    @Override
    public String validateUniqueKeys(Account item, Map<String, Set<String>> context,
                                     Map<String, Set<String>> seenKeys) {
        String username = item.getUsername();
        String email = item.getEmail();
        List<String> errors = new ArrayList<>();

        if (context.get("username").contains(username)) {
            errors.add("Account username already exist");
        } else if (seenKeys.get("username").contains(username)) {
            errors.add("Account username duplicated in CSV file");
        }

        if (context.get("email").contains(email)) {
            errors.add("Account email already exist");
        } else if (seenKeys.get("email").contains(email)) {
            errors.add("Account email duplicated in CSV file");
        }

        if (Objects.nonNull(item.getDepartment())
                && !context.get("departmentId").contains(String.valueOf(item.getDepartment().getDepartmentId()))) {
            errors.add("Department don't exist");
        }

        if (Objects.nonNull(item.getPosition())
                && !context.get("positionId").contains(String.valueOf(item.getPosition().getPositionId()))) {
            errors.add("Position don't exist");
        }

        return errors.isEmpty() ? null : String.join(",", errors);
    }

    @Override
    public int saveAll(List<Account> items) {
        return accountRepository.saveAll(items);
    }

    private String validateAccount(Account account) {
        String fieldError = validateFields(account);
        if (Objects.nonNull(fieldError)) {
            return fieldError;
        }

        List<String> errors = new ArrayList<>();
        if (Objects.nonNull(accountRepository.findByUsername(account.getUsername()))) {
            errors.add("Account username already exist");
        }

        if (Objects.nonNull(accountRepository.findByEmail(account.getEmail()))) {
            errors.add("Account email already exist");
        }

        if (Objects.nonNull(account.getDepartment())
                && Objects.isNull(departmentService.getDepartmentById(account.getDepartment().getDepartmentId()))) {
            errors.add("Department don't exist");
        }

        if (Objects.nonNull(account.getPosition())
                && Objects.isNull(positionService.getPositionById(account.getPosition().getPositionId()))) {
            errors.add("Position don't exist");
        }

        return errors.isEmpty() ? null : String.join(",", errors);
    }

    @Override
    public String validateFields(Account account) {
        if (Objects.isNull(account)) {
            return "Account can't be null";
        }

        List<String> errors = new ArrayList<>();
        if (!ValidationUtils.isValidLength(account.getUsername(), MIN_LENGTH, MAX_LENGTH)) {
            errors.add(String.format("Account username length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (!ValidationUtils.isValidLength(account.getEmail(), MIN_LENGTH, MAX_LENGTH)) {
            errors.add(String.format("Account email length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        if (!ValidationUtils.isValidEmail(account.getEmail())) {
            errors.add("Account email is not valid!");
        }

        if (!ValidationUtils.isValidLength(account.getFullName(), MIN_LENGTH, MAX_LENGTH)) {
            errors.add(String.format("Account fullname length must from %d to %d", MIN_LENGTH, MAX_LENGTH));
        }

        return errors.isEmpty() ? null : String.join(",", errors);
    }
}