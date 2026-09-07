package backend;

import java.util.ArrayList;
import java.util.List;
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

    public void themTaiLieu(TaiLieu taiLieu) {
        dsTaiLieu.add(taiLieu);
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
        hienThiDanhSach(dsTaiLieu);
    }

    public void hienThiDanhSach(List<TaiLieu> ds) {
        if (ds.isEmpty()) {
            System.out.println("Danh sách tài liệu trống!");
            return;
        }

        String khung = String.format("+%-14s+%-22s+%-10s+%-11s+%-22s+%-10s+%-14s+%-12s+%-14s+",
                "", "", "", "", "", "", "", "", "").replace(' ', '-');
        String tieuDe = "| %-12s | %-20s | %-8s | %-9s | %-20s | %-8s | %-12s | %-10s | %-12s |%n";
        String dong = "| %-12s | %-20s | %-8d | %-9s | %-20s | %-8s | %-12s | %-10s | %-12s |%n";

        System.out.println(khung);
        System.out.printf(tieuDe, "Mã tài liệu", "Tên NXB", "Số bản", "Loại", "Tác giả",
                "Số trang", "Số phát hành", "Tháng PH", "Ngày PH");
        System.out.println(khung);

        for (TaiLieu tl : ds) {
            String tacGia = "", soTrang = "", soPhatHanh = "", thangPhatHanh = "", ngayPhatHanh = "";
            if (tl instanceof Sach) {
                Sach sach = (Sach) tl;
                tacGia = sach.getTenTacGia();
                soTrang = String.valueOf(sach.getSoTrang());
            } else if (tl instanceof TapChi) {
                TapChi tapChi = (TapChi) tl;
                soPhatHanh = String.valueOf(tapChi.getSoPhatHanh());
                thangPhatHanh = tapChi.getThangPhatHanh().getTen();
            } else if (tl instanceof Bao) {
                Bao bao = (Bao) tl;
                ngayPhatHanh = bao.getNgayPhatHanh();
            }
            System.out.printf(dong, tl.getMaTaiLieu(), tl.getTenNhaXuatBan(), tl.getSoBanPhatHanh(),
                    tl.getLoai().getTen(), tacGia, soTrang, soPhatHanh, thangPhatHanh, ngayPhatHanh);
        }

        System.out.println(khung);
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

        List<TaiLieu> ketQua = new ArrayList<>();
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getLoai() == loai) {
                ketQua.add(tl);
            }
        }

        if (ketQua.isEmpty()) {
            System.out.println("Không có tài liệu loại " + loai.getTen() + "!");
        } else {
            hienThiDanhSach(ketQua);
        }
    }
}
