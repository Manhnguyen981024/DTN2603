package entity;

import java.util.Scanner;

import entity.enums.LoaiTaiLieu;
import entity.enums.Thang;

public class TapChi extends TaiLieu {
    private int soPhatHanh;
    private Thang thangPhatHanh;

    public TapChi() {}

    public TapChi(String maTaiLieu, String tenNhaXuatBan, int soBanPhatHanh,
                  int soPhatHanh, Thang thangPhatHanh) {
        super(maTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.soPhatHanh = soPhatHanh;
        this.thangPhatHanh = thangPhatHanh;
    }

    public int getSoPhatHanh() {
        return soPhatHanh;
    }

    public void setSoPhatHanh(int soPhatHanh) {
        this.soPhatHanh = soPhatHanh;
    }

    public Thang getThangPhatHanh() {
        return thangPhatHanh;
    }

    public void setThangPhatHanh(Thang thangPhatHanh) {
        this.thangPhatHanh = thangPhatHanh;
    }

    @Override
    public LoaiTaiLieu getLoai() {
        return LoaiTaiLieu.TAP_CHI;
    }

    @Override
    public void nhapThongTin(Scanner sc) {
        super.nhapThongTin(sc);
        while (true) {
            System.out.print("Số phát hành: ");
            try {
                soPhatHanh = Integer.parseInt(sc.nextLine());
                if (soPhatHanh > 0) {
                    break;
                }
                System.out.println("Số phát hành phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }
        System.out.println("Chọn tháng phát hành:");
        for (int i = 0; i < Thang.values().length; i++) {
            System.out.println((i + 1) + ". " + Thang.values()[i].getTen());
        }
        while (true) {
            System.out.print("Lựa chọn (1-12): ");
            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice >= 1 && choice <= 12) {
                    thangPhatHanh = Thang.values()[choice - 1];
                    break;
                }
                System.out.println("Vui lòng nhập số từ 1 đến 12!");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }
    }

    @Override
    public void hienThi() {
        System.out.println("== TẠP CHÍ ==");
        super.hienThi();
        System.out.println("Số phát hành: " + soPhatHanh);
        System.out.println("Tháng phát hành: " + thangPhatHanh.getTen());
    }
}
