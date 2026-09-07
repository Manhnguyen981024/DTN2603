package backend;

import java.util.ArrayList;
import java.util.Scanner;

import entity.Bao;
import entity.enums.LoaiTaiLieu;
import entity.Sach;
import entity.TaiLieu;
import entity.TapChi;

public class QuanLySach implements IQuanLySach {
    private final ArrayList<TaiLieu> dsTaiLieu = new ArrayList<>();

    private boolean maTrungLap(String ma) {
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getMaTaiLieu().equalsIgnoreCase(ma)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void themTaiLieu(Scanner sc) {
        System.out.println("Chọn loại tài liệu:");
        System.out.println("1. Sách");
        System.out.println("2. Tạp chí");
        System.out.println("3. Báo");
        int choice;
        while (true) {
            System.out.print("Lựa chọn (1-3): ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= 3) {
                    break;
                }
                System.out.println("Vui lòng nhập số từ 1 đến 3!");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }

        TaiLieu taiLieu = null;
        switch (choice) {
            case 1:
                taiLieu = new Sach();
                break;
            case 2:
                taiLieu = new TapChi();
                break;
            case 3:
                taiLieu = new Bao();
                break;
        }

        String ma;
        while (true) {
            System.out.print("Mã tài liệu: ");
            ma = sc.nextLine();
            if (ma.isBlank()) {
                System.out.println("Mã tài liệu không được để trống!");
                continue;
            }
            if (maTrungLap(ma)) {
                System.out.println("Mã tài liệu đã tồn tại, vui lòng nhập mã khác!");
                continue;
            }
            break;
        }
        taiLieu.setMaTaiLieu(ma);
        taiLieu.nhapThongTin(sc);

        dsTaiLieu.add(taiLieu);
        System.out.println("Thêm tài liệu thành công!");
    }

    @Override
    public void xoaTheoMa(Scanner sc) {
        System.out.print("Nhập mã tài liệu cần xóa: ");
        String ma = sc.nextLine();
        boolean removed = dsTaiLieu.removeIf(tl -> tl.getMaTaiLieu().equalsIgnoreCase(ma));
        if (removed) {
            System.out.println("Đã xóa tài liệu thành công!");
        } else {
            System.out.println("Không tìm thấy tài liệu có mã " + ma + "!");
        }
    }

    @Override
    public void hienThiDanhSach() {
        if (dsTaiLieu.isEmpty()) {
            System.out.println("Danh sách tài liệu trống!");
            return;
        }
        for (TaiLieu tl : dsTaiLieu) {
            tl.hienThi();
            System.out.println("--------------------------");
        }
    }

    @Override
    public void timKiemTheoLoai(Scanner sc) {
        System.out.println("Chọn loại tài liệu cần tìm:");
        System.out.println("1. Sách");
        System.out.println("2. Tạp chí");
        System.out.println("3. Báo");
        int choice;
        while (true) {
            System.out.print("Lựa chọn (1-3): ");
            try {
                choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= 3) {
                    break;
                }
                System.out.println("Vui lòng nhập số từ 1 đến 3!");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }

        LoaiTaiLieu loai;
        switch (choice) {
            case 1:
                loai = LoaiTaiLieu.SACH;
                break;
            case 2:
                loai = LoaiTaiLieu.TAP_CHI;
                break;
            default:
                loai = LoaiTaiLieu.BAO;
                break;
        }

        boolean found = false;
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getLoai() == loai) {
                tl.hienThi();
                System.out.println("--------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không có tài liệu loại " + loai.getTen() + "!");
        }
    }
}
