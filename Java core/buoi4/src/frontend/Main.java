package frontend;

import java.util.Scanner;

import backend.QuanLySach;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        QuanLySach quanLySach = new QuanLySach();
        menu(sc, quanLySach);
        sc.close();
    }

    public static void menu(Scanner sc, QuanLySach quanLySach) {
        int choice;

        do {
            System.out.println("\n===== QUẢN LÝ TÀI LIỆU =====");
            System.out.println("1. Thêm mới tài liệu");
            System.out.println("2. Xóa tài liệu theo mã");
            System.out.println("3. Hiển thị danh sách tài liệu");
            System.out.println("4. Tìm kiếm tài liệu theo loại");
            System.out.println("5. Thoát");
            while (true) {
                System.out.print("Lựa chọn (1-5): ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                    if (choice >= 1 && choice <= 5) {
                        break;
                    }
                    System.out.println("Vui lòng nhập số từ 1 đến 5!");
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập một số hợp lệ!");
                }
            }

            switch (choice) {
                case 1:
                    quanLySach.themTaiLieu(sc);
                    break;
                case 2:
                    quanLySach.xoaTheoMa(sc);
                    break;
                case 3:
                    quanLySach.hienThiDanhSach();
                    break;
                case 4:
                    quanLySach.timKiemTheoLoai(sc);
                    break;
                case 5:
                    System.out.println("Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 5);
    }
}