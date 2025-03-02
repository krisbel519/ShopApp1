import com.google.inject.Guice;
import com.google.inject.Injector;
import config.AppModule;
import service.UserService;
import service.ProductService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        UserService userService = injector.getInstance(UserService.class);
        ProductService productService = injector.getInstance(ProductService.class);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Rejestracja\n2. Logowanie\n3. Wyjście\nWybierz opcję:");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Oczyszczenie bufora

            if (choice == 1) {
                System.out.print("Podaj nazwę użytkownika: ");
                String username = scanner.nextLine();
                System.out.print("Podaj hasło: ");
                String password = scanner.nextLine();
                userService.registerUser(username, password);
            } else if (choice == 2) {
                System.out.print("Podaj nazwę użytkownika: ");
                String username = scanner.nextLine();
                System.out.print("Podaj hasło: ");
                String password = scanner.nextLine();
                if (userService.loginUser(username, password)) {
                    System.out.println("Zalogowano pomyślnie!");
                    runShopMenu(scanner, productService);  // Меню магазина после входа
                } else {
                    System.out.println("Błędna nazwa użytkownika lub hasło.");
                }
            } else if (choice == 3) {
                System.out.println("Zamykanie aplikacji...");
                break;
            } else {
                System.out.println("Nieprawidłowa opcja, spróbuj ponownie.");
            }
        }
    }

    private static void runShopMenu(Scanner scanner, ProductService productService) {
        while (true) {
            System.out.println("\n1. Pokaż produkty\n2. Wyjdź z konta\nWybierz opcję:");
            int option = scanner.nextInt();
            scanner.nextLine(); // Oczyszczenie bufora

            if (option == 1) {
                productService.listProducts();
            } else if (option == 2) {
                System.out.println("Wylogowano.");
                break;
            } else {
                System.out.println("Nieprawidłowa opcja, spróbuj ponownie.");
            }
        }
    }
}
