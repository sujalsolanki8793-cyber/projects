
import java.util.*;

class Management{

    static Scanner sc = new Scanner(System.in);

    static void bookRooms() {

        System.out.println("-------------------------");
        System.out.println("1. Room For Single");
        System.out.println("-------------------------");
        System.out.println("2. Room For Two");
        System.out.println("-------------------------");
        System.out.print("Please Enter Your Choice :");
        int num = sc.nextInt();
        switch (num) {
            case 1:
                singleRoom();
                break;
            case 2:
                doubleRoom();
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
        }
    }

    static void doubleRoom() {
        System.out.println("-------------------------");
        System.out.println("Enter Size Of Room:");
        System.out.println("-------------------------");
        System.out.println("1. Regular Size Bedroom");
        System.out.println("-------------------------");
        System.out.println("2. King Size Bedroom");
        System.out.println("-------------------------");
        System.out.print("Enter Your Choice : ");

        int size = sc.nextInt();
        switch (size) {
            case 1:
                calculateTotalCost("Double", 2999);
                break;
            case 2:
                calculateTotalCost("Double", 5999);
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
        }
		System.out.print("Confirm Booking (true/false): ");
        boolean confirm = sc.nextBoolean();
        if (confirm) {
            System.out.println("|| Your room is booked ||");
        } else {
            System.out.println("|| Booking canceled ||");
        }
    }

    static void singleRoom() {
        System.out.println("-------------------------");
        System.out.println("Enter Size Of Room:");
        System.out.println("-------------------------");
        System.out.println("1. Regular Size Bedroom");
        System.out.println("-------------------------");
        System.out.println("2. King Size Bedroom");
        System.out.println("-------------------------");
        System.out.print("Enter Your Choice : ");
        int size = sc.nextInt();
        switch (size) {
            case 1:
                calculateTotalCost("Single", 1999);
                break;
            case 2:
                calculateTotalCost("Single", 4999);
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
        }

        System.out.print("Confirm Booking (true/false): ");
        boolean confirm = sc.nextBoolean();
        if (confirm) {
            System.out.println("|| Your room is booked ||");
        } else {
            System.out.println("|| Booking canceled ||");
        }
    }

    static void calculateTotalCost(String roomType, int pricePerNight) {
        System.out.print("Enter Number Of Nights: ");
        int nights = sc.nextInt();
        int total = nights * pricePerNight;
        System.out.println("Total Cost for " + roomType + " Room: " + total + " Rs");
    }
}

class Restaurant extends Management {

    static Scanner sc = new Scanner(System.in);

    static void orderFood() {
        System.out.println("-------------------------");
        System.out.println("Welcome To The Restaurant!");
        System.out.println("-------------------------");
        System.out.println("Menu : ");
        System.out.println("-------------------------");
        System.out.println("1. South Indian Cuisine");
        System.out.println("-------------------------");
        System.out.println("2. Italian Cuisine");
        System.out.println("-------------------------");
        System.out.println("3. Punjabi Cuisine");
        System.out.println("-------------------------");
        System.out.println("4. Chinese Cuisine");
        System.out.println("-------------------------");
        System.out.print("Enter Your Choice : ");
        int ch = sc.nextInt();
        switch (ch) {
            case 1:
                southIndian();
                break;
            case 2:
                italian();
                break;
            case 3:
                pubjabi();
                break;
            case 4:
                chinese();
                break;
            default:
                System.out.println("Enter choice between 1 to 4 only");
                break;
        }
    }

    static void southIndian() {
        System.out.println("-------------------------");
        System.out.println("Here is the menu for south indian cuisine!");
        System.out.println("-------------------------");
        System.out.println("1. To Order Idli");
        System.out.println("-------------------------");
        System.out.println("2. To Order Dosa");
        System.out.println("-------------------------");
        System.out.println("3. To Order Uttapaa");
        System.out.println("-------------------------");
        System.out.print("Enter Your Choice : ");
        int n = sc.nextInt();
        switch (n) {
            case 1:
                System.out.println("-------------------------");
                System.out.println("Which type of idli do you want?");
                System.out.println("-------------------------");
                System.out.println("1. To Order plain Idli");
                System.out.println("-------------------------");
                System.out.println("2. To Order masala Idli");
                System.out.println("-------------------------");
                System.out.println("3. To Order fried idli");
                System.out.println("-------------------------");
                System.out.print("Enter Your Choice : ");
                int c1 = sc.nextInt();
                switch (c1) {
                    case 1:
                        System.out.println("Plain idli is 99 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n1 = sc.nextInt();
                        int T1;
                        T1 = 99 * n1;
                        System.out.println("Total cost of Plain idli:" + T1);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Masala idli is 149 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n2 = sc.nextInt();
                        int T2;
                        T2 = 149 * n2;
                        System.out.println("Total cost of Masala idli:" + T2);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Fried idli is 199 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n3 = sc.nextInt();
                        int T3;
                        T3 = 199 * n3;
                        System.out.println("Total cost of Fried idli:" + T3);
                        System.out.println("|| Your Order Placed Succesfully ||");
                }
                break;
            case 2:
                System.out.println("-------------------------");
                System.out.println("Which type of Dosa you want?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Plain Dosa");
                System.out.println("-------------------------");
                System.out.println("2. To Order Masala Dosa");
                System.out.println("-------------------------");
                System.out.println("3. To Order Rava Dosa");
                System.out.println("-------------------------");
                int c2 = sc.nextInt();
                switch (c2) {
                    case 1:
                        System.out.println("Price of a Plain Dosa is 149 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n4 = sc.nextInt();
                        int T4;
                        T4 = 149 * n4;
                        System.out.println("Total cost of Plain Dosa:" + T4);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Masala Dosa is 199 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n5 = sc.nextInt();
                        int T5;
                        T5 = 199 * n5;
                        System.out.println("Total cost of Masala Dosa:" + T5);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Rava Dosa is 199 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n6 = sc.nextInt();
                        int T6;
                        T6 = 199 * n6;
                        System.out.println("Total cost of Rava Dosa:" + T6);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            case 3:
                System.out.println("-------------------------");
                System.out.println("which type of uttapaa you want?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Plain Uttapaa");
                System.out.println("-------------------------");
                System.out.println("2. To Order Masala Uttapaa");
                System.out.println("-------------------------");
                System.out.println("3. To Order Veg Uttapaa");
                System.out.println("-------------------------");
                int c3 = sc.nextInt();
                switch (c3) {
                    case 1:
                        System.out.println("Price of Plain Uttapaa is 99 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n7 = sc.nextInt();
                        int T7;
                        T7 = 99 * n7;
                        System.out.println("Total cost of Plain Uttapaa:" + T7);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of masala uttapaa is 149 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n8 = sc.nextInt();
                        int T8;
                        T8 = 149 * n8;
                        System.out.println("Total cost of Masala Uttapaa:" + T8);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Veg Uttapaa is 149 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n9 = sc.nextInt();
                        int T9;
                        T9 = 149 * n9;
                        System.out.println("Total cost of Veg Uttapaa:" + T9);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            default:
                System.out.println("Invalid choice. Please enter choice between 1 to 3 only");

        }
    }

    static void italian() {
        System.out.println("-------------------------");
        System.out.println("Here is a Italian Menu!");
        System.out.println("-------------------------");
        System.out.println("Enter 1 To Order Pizza");
        System.out.println("-------------------------");
        System.out.println("Enter 2 To Order Pasta");
        System.out.println("-------------------------");
        System.out.println("Enter 3 To Order Soup");
        System.out.println("-------------------------");
        int n2 = sc.nextInt();
        switch (n2) {
            case 1:
                System.out.println("Which type of Pizza you want to order?");
                System.out.println("1. To Order Margeretta Pizza");
                System.out.println("2. To Order Veg pizza");
                System.out.println("3. To Order Garlic bread");
                int c4 = sc.nextInt();
                switch (c4) {
                    case 1:
                        System.out.println("Price of Margeretta Pizza is 369 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N1 = sc.nextInt();
                        int t1;
                        t1 = 369 * N1;
                        System.out.println("Total cost of Margeretta Pizza:" + t1);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Veg Pizza 349 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N2 = sc.nextInt();
                        int t2;
                        t2 = 349 * N2;
                        System.out.println("Total cost of Veg Pizza:" + t2);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Garlic Bread is 199 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N3 = sc.nextInt();
                        int t3;
                        t3 = 199 * N3;
                        System.out.println("Total cost of Garlic Bread:" + t3);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            case 2:
                System.out.println("-------------------------");
                System.out.println("Which type of Pasta you want to order?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Macroni");
                System.out.println("-------------------------");
                System.out.println("2. To Order Agli E Olio Pasta");
                System.out.println("-------------------------");
                System.out.println("3. To Order Fettuccine Alfredo Pasta");
                System.out.println("-------------------------");
                int c5 = sc.nextInt();
                switch (c5) {
                    case 1:
                        System.out.println("Price of a Macroni is 399 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N4 = sc.nextInt();
                        int t4;
                        t4 = 399 * N4;
                        System.out.println("Total cost of e Macroni:" + t4);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Agli E Olio Pasta is 459 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N5 = sc.nextInt();
                        int t5;
                        t5 = 459 * N5;
                        System.out.println("Total cost of Agli E Olio Pasta:" + t5);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Fettuccine Alfredo Pasta 459 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N6 = sc.nextInt();
                        int t6;
                        t6 = 459 * N6;
                        System.out.println("Total cost of Fettuccine Alfredo Pasta:" + t6);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            case 3:
                System.out.println("-------------------------");
                System.out.println("Which type of Soup you want to order?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Tomato soup");
                System.out.println("-------------------------");
                System.out.println("2. To Order Hot & sour soup");
                System.out.println("-------------------------");
                System.out.println("3. To Order Manchow soup");
                System.out.println("-------------------------");
                int c6 = sc.nextInt();
                switch (c6) {
                    case 1:
                        System.out.println("Price of Tomato Soup is 199 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N7 = sc.nextInt();
                        int t7;
                        t7 = 199 * N7;
                        System.out.println("Total cost of Tomato Soup:" + t7);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Hot & Sour Soup is 199 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N8 = sc.nextInt();
                        int t8;
                        t8 = 199 * N8;
                        System.out.println("Total cost of Hot & Sour Soup:" + t8);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Manchow Soup is 229 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int N9 = sc.nextInt();
                        int t9;
                        t9 = 229 * N9;
                        System.out.println("Total cost of Manchow Soup:" + t9);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            default:
                System.out.println("Enter choice between 1 to 3 only");

        }
    }

    static void pubjabi() {
        System.out.println("-------------------------");
        System.out.println("Here is the menu for pubjabi cuisine!");
        System.out.println("-------------------------");
        System.out.println("1. To Order Punjabi Sabji");
        System.out.println("-------------------------");
        System.out.println("2. To Order Chapati");
        System.out.println("-------------------------");
        System.out.println("3. To Order Lassi");
        System.out.println("-------------------------");
        int n3 = sc.nextInt();
        switch (n3) {
            case 1:
                System.out.println("-------------------------");
                System.out.println("Which type of Punjabi Sabji you want?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Paneer Bhruji");
                System.out.println("-------------------------");
                System.out.println("2. To Order Panner Butter Masala");
                System.out.println("-------------------------");
                System.out.println("3. To Order Panner Patiala");
                System.out.println("-------------------------");
                int c7 = sc.nextInt();
                switch (c7) {
                    case 1:
                        System.out.println("Price of Panner Bhurji 339 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n4 = sc.nextInt();
                        int T4;
                        T4 = 339 * n4;
                        System.out.println("Total cost of Panner Bhurji:" + T4);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Panner Butter Masala is 329 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n5 = sc.nextInt();
                        int T5;
                        T5 = 329 * n5;
                        System.out.println("Total cost of Panner Butter Masala:" + T5);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Panner Patiala is 369 rs");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n6 = sc.nextInt();
                        int T6;
                        T6 = 369 * n6;
                        System.out.println("Total cost of Panner Patiala:" + T6);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            case 2:
                System.out.println("-------------------------");
                System.out.println("Which type of Chapati you want?");
                System.out.println("-------------------------");
                System.out.println("1 To Order Plain Chapati");
                System.out.println("-------------------------");
                System.out.println("2 To Order Butter Chapati");
                System.out.println("-------------------------");
                int c8 = sc.nextInt();
                switch (c8) {
                    case 1:
                        System.out.println("Price of a Plain Chapati 59 rs");
                        System.out.println("How many Chapatis do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n7 = sc.nextInt();
                        int T7;
                        T7 = 59 * n7;
                        System.out.println("Total cost of Plain Chapati:" + T7);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Butter Chapati is 79 rs");
                        System.out.println("How many Chapatis do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n8 = sc.nextInt();
                        int T8;
                        T8 = 79 * n8;
                        System.out.println("Total cost of Butter Chapati:" + T8);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                }
                break;
            case 3:
                System.out.println("-------------------------");
                System.out.println("Which type of Lassi you want?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Plain Lassi");
                System.out.println("-------------------------");
                System.out.println("2. To Order Masala Kesar Lassi");
                System.out.println("-------------------------");
                System.out.println("3. To Order Rajbhog Lassi");
                System.out.println("-------------------------");
                int c9 = sc.nextInt();
                switch (c9) {
                    case 1:
                        System.out.println("Price of Plain Lassi is 99 rs per each");
                        System.out.println("How many glasses do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n10 = sc.nextInt();
                        int T10;
                        T10 = 99 * n10;
                        System.out.println("Total cost of Plain Lassi:" + T10);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Keshar Lassi is 149 rs per each");
                        System.out.println("How many glasses do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n11 = sc.nextInt();
                        int T11;
                        T11 = 149 * n11;
                        System.out.println("Total cost of Keshar Lassi:" + T11);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Rajbhog Lassi is 199 rs per each");
                        System.out.println("How many glasses do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n12 = sc.nextInt();
                        int T12;
                        T12 = 199 * n12;
                        System.out.println("Total cost of Rajbhog Lassi:" + T12);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            default:
                System.out.println("Enter choice between 1 to 3 only");

        }
    }

    static void chinese() {
        System.out.println("-------------------------");
        System.out.println("Here is the menu for Chinese cuisine!");
        System.out.println("-------------------------");
        System.out.println("1. To Order Manchurian");
        System.out.println("-------------------------");
        System.out.println("2. To Order Noodles");
        System.out.println("-------------------------");
        System.out.println("3. To Order Fried rice");
        System.out.println("-------------------------");
        int n4 = sc.nextInt();
        switch (n4) {
            case 1:
                System.out.println("-------------------------");
                System.out.println("Which type of Manchurian you want?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Manchurian Dry");
                System.out.println("-------------------------");
                System.out.println("2. To Order Manchurian Gravy");
                System.out.println("-------------------------");
                System.out.print("Enter Your Choice : ");
                int c10 = sc.nextInt();
                switch (c10) {
                    case 1:
                        System.out.println("Price of Manchurian Dry is 229 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n13 = sc.nextInt();
                        int T13;
                        T13 = 229 * n13;
                        System.out.println("Total cost of Manchurian Dry:" + T13);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Manchurian Gravy is 269 rs");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n14 = sc.nextInt();
                        int T14;
                        T14 = 269 * n14;
                        System.out.println("Total cost of Manchurian Gravy:" + T14);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                }
                break;
            case 2:
                System.out.println("-------------------------");
                System.out.println("Which type of Noodles you want?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Hakka Noodles");
                System.out.println("-------------------------");
                System.out.println("2. To Order Shezwan Noodles");
                System.out.println("-------------------------");
                System.out.println("3. To Order Manchurian Noodles");
                System.out.println("-------------------------");
                System.out.print("Enter Your Choice : ");
                int c11 = sc.nextInt();
                switch (c11) {
                    case 1:
                        System.out.println("Price of a Hakka Noodles is 199 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n16 = sc.nextInt();
                        int T16;
                        T16 = 199 * n16;
                        System.out.println("Total cost of Hakka Noodles:" + T16);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Shezwan Noodles is 249 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n17 = sc.nextInt();
                        int T17;
                        T17 = 249 * n17;
                        System.out.println("Total cost of Shezwan Noodles:" + T17);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Manchurian Noodles is 299 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n18 = sc.nextInt();
                        int T18;
                        T18 = 299 * n18;
                        System.out.println("Total cost of Manchurian Noodles:" + T18);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            case 3:
                System.out.println("-------------------------");
                System.out.println("Which type of Fried Rice you want to order?");
                System.out.println("-------------------------");
                System.out.println("1. To Order Veg Fry Rice");
                System.out.println("-------------------------");
                System.out.println("2. To Order Sajwaan Fry Rice");
                System.out.println("-------------------------");
                System.out.println("3. To Order Paneer Fry Rice");
                System.out.println("-------------------------");
                System.out.print("Enter Your Choice : ");
                int c12 = sc.nextInt();
                switch (c12) {
                    case 1:
                        System.out.println("Price of Veg Fry Rice 199 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n19 = sc.nextInt();
                        int T19;
                        T19 = 199 * n19;
                        System.out.println("Total cost of Veg Fry Rice:" + T19);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 2:
                        System.out.println("Price of Sajwan Fry Rice 229 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n20 = sc.nextInt();
                        int T20;
                        T20 = 299 * n20;
                        System.out.println("Total cost of Sajwan Fry Rice:" + T20);
                        System.out.println("|| Your Order Placed Succesfully ||");
                        break;
                    case 3:
                        System.out.println("Price of Paneer Fry Rice is 249 rs per each");
                        System.out.println("How many plates do you want to order?");
                        System.out.print("Enter Your Choice : ");
                        int n21 = sc.nextInt();
                        int T21;
                        T21 = 249 * n21;
                        System.out.println("Total cost of Paneer Fry Rice:" + T21);
                        System.out.println("|| Your Order Placed Succesfully ||");

                }
                break;
            default:
                System.out.println("Enter choice between 1 to 3 only");

        }
    }

    public static void main(String[] args) {
        boolean isRunning = true;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome To The Hotel Management System!");
        System.out.println("-------------------------");
        while (isRunning) {
            System.out.println("1. To Book Rooms");
            System.out.println("-------------------------");
            System.out.println("2. To Order Food");
            System.out.println("-------------------------");
            System.out.println("3. To Exit");
            System.out.println("-------------------------");
            System.out.print("Enter Your Choice : ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    bookRooms();
                    break;
                case 2:
                    orderFood();
                    break;
                case 3:
                    System.out.println("|| Thank You For Stepping By, Have A Great Time. ||");
                    System.out.println("-------------------------");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    System.out.println("-------------------------");
            }
        }
    }
}
