package entity;

import java.util.Scanner;

import entity.enums.LoaiTaiLieu;

public class Sach extends TaiLieu {
    private String tenTacGia;
    private int soTrang;

    public Sach() {}

    public Sach(String maTaiLieu, String tenNhaXuatBan, int soBanPhatHanh,
                String tenTacGia, int soTrang) {
        super(maTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.tenTacGia = tenTacGia;
        this.soTrang = soTrang;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    @Override
    public LoaiTaiLieu getLoai() {
        return LoaiTaiLieu.SACH;
    }

    @Override
    public void nhapThongTin(Scanner sc) {
        super.nhapThongTin(sc);
        System.out.print("Tên tác giả: ");
        tenTacGia = sc.nextLine();
        while (true) {
            System.out.print("Số trang: ");
            try {
                soTrang = Integer.parseInt(sc.nextLine());
                if (soTrang > 0) {
                    break;
                }
                System.out.println("Số trang phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }
    }

    @Override
    public void hienThi() {
        System.out.println("== SÁCH ==");
        super.hienThi();
        System.out.println("Tên tác giả: " + tenTacGia);
        System.out.println("Số trang: " + soTrang);
    }
}
