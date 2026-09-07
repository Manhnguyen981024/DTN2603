package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import entity.enums.LoaiTaiLieu;

public class Bao extends TaiLieu {
    private String ngayPhatHanh;

    public Bao() {}

    public String getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(String ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }

    @Override
    public LoaiTaiLieu getLoai() {
        return LoaiTaiLieu.BAO;
    }

    @Override
    public void nhapThongTin(Scanner sc) {
        super.nhapThongTin(sc);
        DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print("Ngày phát hành (dd/mm/yyyy): ");
            String input = sc.nextLine();
            try {
                LocalDate.parse(input, dinhDang);
                ngayPhatHanh = input;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Ngày phát hành không đúng định dạng dd/mm/yyyy, vui lòng nhập lại!");
            }
        }
    }
}