public void start() {
    Scanner scanner = ScannerUtil.getScanner();
    while (true) {
        System.out.println("\n=== AlgoCloud CLI ===");
        System.out.println("1. Add Custom Problem");
        System.out.println("2. View All Problems");
        System.out.println("3. Practice Problems");
        System.out.println("4. Exit");
        System.out.print("Choose option: ");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> AddProblem.add(scanner);
            case 2 -> ViewProblems.display();
            case 3 -> PracticeProblem.start(scanner);
            case 4 -> {
                System.out.println("Exiting...");
                return;
            }
            default -> System.out.println("Invalid option.");
        }
    }
}

public static void main(String[] args) {
    new MainMenu().start();
}
