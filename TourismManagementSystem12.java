import org.w3c.dom.ls.LSOutput;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.io.*;
import java.util.Date;

// --- Tourist Class ---
class Tourist {
    int id;
    String name, gender, address, email, contact, password;
    int age;
    Tourist(String name, String gender, String address, String email, String contact, int age, String password)
    {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.contact = contact;
        this.age = age;
        this.password = password;
    }
}
// --- Tour Package Class ---
class TourPackage {
    int id, duration;
    String title;
    Date startdate;
    Date enddate;
    double price;
    String imagePath;
    TourPackage(int id, String title, int duration, double price,  String imagePath) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.startdate=startdate;
        this.enddate=enddate;
        this.price = price;
        this.imagePath = imagePath;
    }
}
// --- Booking Class ---
class Booking {
    int bookingId, touristId, packageId;
    String bookingDate, status;
    Booking(int bookingId, int touristId, int packageId, String bookingDate, String status) {
        this.bookingId = bookingId;
        this.touristId = touristId;
        this.packageId = packageId;
        this.bookingDate = bookingDate;
        this.status = status;
    }
}
// --- Payment Class ---
class Payment {
    int paymentId, bookingId;
    double amount;
    String date, status, mode;
    Payment(int paymentId, int bookingId, double amount, String date, String status, String mode) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.mode = mode;
    }
}
// --- Hotel Class ---
class Hotel {
    int id;
    String name, contact, address;
    double rating;
    Hotel(int id, String name, String contact, String address, double rating) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.rating = rating;
    }
}
// --- Transport Class ---
class Transport {
    int id;
    String mode, provider, contact;
    double cost;
    Transport(int id, String mode, String provider, String contact, double cost) {
        this.id = id;
        this.mode = mode;
        this.provider = provider;
        this.contact = contact;
        this.cost = cost;
    }
}
//--Main Class--
public class TourismManagementSystem {
    static Scanner sc = new Scanner(System.in);
    static Connection conn;
    static Tourist currentTourist = null;
    static Stack<String> cancelLogs = new Stack<>();
    static PriorityQueue<Hotel> hotelQueue = new PriorityQueue<>((h1, h2) -> Double.compare(h2.rating, h1.rating));
    static LinkedList<Payment> paymentHistory = new LinkedList<>();
    static HashMap<Integer, TourPackage> packageMap = new HashMap<>();
    static HashMap<Integer, Transport> transportMap = new HashMap<>();

    public static void main(String[] args) throws SQLException {
        connectDB();
        loadPackages();
        loadTransports();
        mainMenu();
    }
    //--Connection Code--
    static void connectDB() {
        String url = "jdbc:mysql://localhost:3306/tourism";
        String user = "root";
        String password = "";
        try {
            conn = DriverManager.getConnection(url,user,password);
            System.out.println(" Connected to DB");
        }
        catch (SQLException e) {
            System.out.println(" DB connection failed: " + e.getMessage());
            System.exit(1);
        }
    }
    //--Load Packages--
    static void loadPackages() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tourpackage");
            while (rs.next()) {
                packageMap.put(rs.getInt("tpid"), new TourPackage(
                        rs.getInt("tpid"), rs.getString("title"), rs.getInt("duration"),
                        rs.getDouble("price"),
                        rs.getString("imagePath")
                ));
            }
        }
        catch (SQLException e) {
            System.out.println(" Failed to load tour packages: " + e);
        }
    }
    //--load Transports--
    static void loadTransports() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM transport");
            while (rs.next())
            {
                transportMap.put(rs.getInt("id"), new Transport(
                        rs.getInt("id"), rs.getString("mode"),
                        rs.getString("provider"), rs.getString("contact"),
                        rs.getDouble("cost")));
            }
        }
        catch (SQLException e)
        {
            System.out.println(" Failed to load transports: " + e);
        }
    }
    //--Main Menu--
    static void mainMenu() throws SQLException {
        while (true) {
            System.out.println("\n---------- Tourism Management System------------");
            System.out.println("1. User Registration");
            System.out.println("2. User Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");

            int choice = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Enter Your Choice : ");
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    if (choice >= 1 && choice <= 4) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
            }

            switch (choice) {
                case 1 -> registerTourist();
                case 2 -> loginTourist();
                case 3 -> {
                    while (true) {
                        System.out.print("Enter Admin Password: ");
                        if (sc.next().equals("admin123")) {
                            adminMenu();
                            break;
                        } else {
                            System.out.println("Incorrect password.");
                        }
                    }
                }
                case 4 -> System.exit(0);
            }
        }
    }
    //--Tourist Registration--
    static void registerTourist() {
        try {
            sc.nextLine();
            String name;
            boolean isValid;
            while (true) {
                System.out.print("Enter your Name: ");
                name = sc.nextLine();
                isValid = true; // assume valid

                // check each character
                for (int i = 0; i < name.length(); i++) {
                    char ch = name.charAt(i);
                    if (!Character.isLetter(ch) && ch != ' ') {
                        isValid = false;
                        break;
                    }
                }

                if (isValid && !name.isEmpty()) {
                    System.out.println(" Name accepted: " + name);
                    break;
                } else {
                    System.out.println(" Invalid name! Use only alphabets and spaces.");
                }
            }
            //sc.close();
            String gender;
            while (true) {
                System.out.print("Gender (Male/Female/Other): ");
                gender = sc.next().trim().toLowerCase();
                if (gender.equals("male") || gender.equals("female") || gender.equals("other")) {
                    //gender = gender.substring(0, 1).toUpperCase() + gender.substring(1);
                    break;
                } else {
                    System.out.println(" Invalid gender. Please  Male, Female, or Other.");
                }
            }
            sc.nextLine();

            System.out.print("Address: ");
            String address = sc.nextLine();

            String email;
            while (true) {
                System.out.print("Email: ");
                email = sc.next();
                if (email.length()==9 && email.contains("@") || email.contains(".com")) {
                    break;
                } else {
                    System.out.println(" Invalid email. Email must end with '@tour.com'.");
                }
            }

            String contact;
            while (true) {
                System.out.print("Contact (10 digits, starts with 9/8/7/6): ");
                contact = sc.next();
                if (contact.length()==10 && (contact.startsWith("9") || (contact.startsWith("8") || (contact.startsWith("7") || (contact.startsWith("6")))))) {
                    break;
                } else {
                    System.out.println(" Invalid mobile number. Try again.");
                }
            }

            String password;
            while (true) {
                System.out.print("Password (min 8 chars, all different characters): ");
                password = sc.next();

                if (password.length() < 8) {
                    System.out.println(" Password too short. Must be at least 8 characters.");
                    continue;
                }

                Set<Character> charSet = new HashSet<>();
                for (char c : password.toCharArray()) {
                    charSet.add(c);
                }

                if (charSet.size() != password.length()) {
                    System.out.println(" Password characters must all be different.");
                } else {
                    break;
                }
            }


            int age;
            while (true) {
                System.out.print("Age: ");
                if (!sc.hasNextInt()) {
                    System.out.println(" Invalid input. Please enter a number.");
                    sc.next();
                    continue;
                }
                age = sc.nextInt();
                if (age > 14 && age <= 80) {
                    break;
                } else {
                    System.out.println(" Invalid age. Please enter a valid age between 14 and 80");
                }
            }

            // Insert into database
            String sql7="{Call registerTourist(?,?,?,?,?,?,?) }";
            CallableStatement cst6=conn.prepareCall(sql7);
            cst6.setString(1, name);
            cst6.setString(2, gender);
            cst6.setString(3, address);
            cst6.setString(4, email);
            cst6.setString(5, contact);
            cst6.setString(6, password);
            cst6.setInt(7, age);
            cst6.executeUpdate();

            System.out.println(" Registration successful!");
        } catch (Exception e) {
            System.out.println(" Registration failed: " + e);
        }
    }
    //--Login as Tourist--
    static String loggedInEmail = null;
    static int loggedInTid =0;
    static void loginTourist() {
        while (true) {
            try {
                System.out.print("Enter Email: ");
                String email = sc.next();

                System.out.print("Enter Password: ");
                String password = sc.next();

                PreparedStatement ps = conn.prepareStatement(
                        "SELECT * FROM tourist WHERE temail=? AND tpassword=?");
                ps.setString(1, email);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    currentTourist = new Tourist(
                            rs.getString("tname"),
                            rs.getString("tgender"),
                            rs.getString("taddress"),
                            rs.getString("temail"),
                            rs.getString("tcontactno"),
                            rs.getInt("tage"),
                            rs.getString("tpassword")
                    );
                     loggedInEmail = email;
                     loggedInTid = rs.getInt("tid");

                    System.out.println(" Welcome, " + currentTourist.name);
                    touristMenu();
                    return;
                } else {
                    System.out.println("\n2" +
                            " Invalid email or password.");
                    System.out.println("1. Try Again");
                    System.out.println("2. Forgot Password");
                    System.out.println("3. Back to Main Menu");
                    System.out.print("Choose option: ");
                    int ch = sc.nextInt();

                    if (ch == 1) {
                        continue;
                    } else if (ch == 2) {
                        forgotPasswordFlow();
                        return;
                    } else {
                        return;
                    }
                }

            } catch (Exception e) {
                System.out.println("Login error: " + e.getMessage());
                return;
            }
        }
    }
    static void forgotPasswordFlow() {
        try {
            System.out.println("\n---  Forgot Password ---");

            String email;
            while (true) {
                System.out.print("Enter your registered Email: ");
                email = sc.nextLine().trim();
                if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) break;
                System.out.println(" Invalid email format. Try again.");
            }

            String contact;
            while (true) {
                System.out.print("Enter your registered Contact Number: ");
                contact = sc.nextLine().trim();
                if (contact.matches("[6789]\\d{9}")) break;
                System.out.println(" Invalid contact number. Must be 10 digits starting with 6-9.");
            }

            String query = "SELECT * FROM tourist WHERE temail = ? AND tcontactno = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, email);
                ps.setString(2, contact);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String newPass, confirmPass;
                        while (true) {
                            System.out.print("Enter new password: ");
                            newPass = sc.nextLine().trim();

                            System.out.print("Confirm new password: ");
                            confirmPass = sc.nextLine().trim();

                            if (!newPass.equals(confirmPass)) {
                                System.out.println(" Passwords do not match. Please try again.");
                            } else if (newPass.length() < 6) {
                                System.out.println(" Password too short. Minimum 6 characters.");
                            } else {
                                break;
                            }
                        }

                        String updateSql = "UPDATE tourist SET tpassword = ? WHERE temail = ? AND tcontactno = ?";
                        try (PreparedStatement update = conn.prepareStatement(updateSql)) {
                            update.setString(1, newPass); //  Consider hashing in production!
                            update.setString(2, email);
                            update.setString(3, contact);

                            int updated = update.executeUpdate();
                            if (updated > 0) {
                                System.out.println(" Password reset successfully!");
                            } else {
                                System.out.println(" Something went wrong. Please try again.");
                            }
                        }
                    } else {
                        System.out.println(" No account found with that email and contact number.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(" Forgot password error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //--Tourist Menu--
    static void touristMenu() {
        sc.nextLine();
        while (true) {
            System.out.println("\n--- Tourist Menu ---");
            System.out.println("1. View Tour Packages");
            System.out.println("2. Book Package");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Transport Options");
            System.out.println("5. Export Booking History");
            System.out.println("6. Give Review About Your Tour");
            System.out.println("7. Logout");
            System.out.print("Enter Your Choice : ");

            try {
                int ch = Integer.parseInt(sc.nextLine().trim()); // safer than nextInt()

                switch (ch) {
                    case 1 -> viewPackages();
                    case 2 -> bookPackage();
                    case 3 -> cancelBooking();
                    case 4 -> viewTransports();
                    case 5 -> exportBookings();
                    case 6 -> reviewPackage();
                    case 7 -> {
                        currentTourist = null;
                        System.out.println(" Logged out successfully.");
                        return;
                    }
                    default -> System.out.println(" Invalid choice. Please select a number from 1 to 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid numeric choice.");
            }
        }
    }

    //--View Packages--
    static void viewPackages() {
        System.out.println("\n--- Available Tour Packages ---");
        try {
            String sql = "{CALL viewPackages()}";
            CallableStatement cst = conn.prepareCall(sql);
            ResultSet rs = cst.executeQuery();
            boolean anyPackageFound = false;

            System.out.println("\nFrom Ahmedabad");

            while (rs.next()) {
                anyPackageFound = true;

                int tpid = rs.getInt("tpid");
                String title = rs.getString("title");
                int duration = rs.getInt("duration");
                double price = rs.getDouble("price");

                System.out.println("Package ID : " + tpid);
                System.out.println("Title      : " + title);
                System.out.println("Duration   : " + duration + " days");
                System.out.println("Price      : ₹" + price);

                // Handling image saving
                try {
                    Blob imgBlob = rs.getBlob("image");
                    if (imgBlob != null && imgBlob.length() > 0) {
                        byte[] imgBytes = imgBlob.getBytes(1, (int) imgBlob.length());
                        String filename = "image" + tpid + ".jpg";
                        try (FileOutputStream fos = new FileOutputStream(filename)) {
                            fos.write(imgBytes);
                        }
                        System.out.println("Image saved as: " + filename);
                    } else {
                        System.out.println("Image      : Not available");
                    }
                } catch (Exception eImg) {
                    System.out.println("Image      : Error saving image (" + eImg.getMessage() + ")");
                }

                // Fetch and display reviews
                String reviewSql =  "SELECT r.rating, r.message, t.tname AS reviewerName FROM review r JOIN tourist t ON r.tid = t.tid WHERE r.tpid = ?";

                try (PreparedStatement pstmt = conn.prepareStatement(reviewSql)) {
                    pstmt.setInt(1, tpid);
                    ResultSet rsReview = pstmt.executeQuery();

                    boolean anyReviewFound = false;
                    System.out.println("Reviews    :");
                    while (rsReview.next()) {
                        anyReviewFound = true;
                        int rating = rsReview.getInt("rating");
                        String message = rsReview.getString("message");
                        String reviewerName = rsReview.getString("reviewerName");

                        System.out.println("  ⭐ " + rating + "/5 by " + reviewerName);
                        System.out.println("    \"" + message + "\"");
                    }

                    if (!anyReviewFound) {
                        System.out.println("  No reviews yet.");
                    }
                } catch (SQLException eReview) {
                    System.out.println("Reviews    : Error fetching reviews (" + eReview.getMessage() + ")");
                }
                System.out.println("---------------------------");
            }
            if (!anyPackageFound) {
                System.out.println("No tour packages available.");
            }
        } catch (SQLException e) {
            System.out.println("Error loading tour packages: " + e.getMessage());
        }
    }

    //--Package Booking--
    static int currentBookingId = -1;
    static int currentTpid = -1;;
    static int currentTransportId = -1;
    static double  hotelPrice =-1;
    static int TourPackageId =-1;
    static LocalDate checkin_date;
    static LocalDate checkOut_date;
    static int tourDay =-1;
    static int id =-1 ;
    static int hid = -1 ;
    static String  tpName =null;
    static String trsnportName = null;
    static String tContact = null;
    static String hotelName = null;
    static String hContact = null;
    static int  room = (int) ((Math.random()+1)*100);

    static void bookPackage() {
        viewPackages(); // Show available packages

        try {
            conn.setAutoCommit(false);
            int tid = loggedInTid;
            int tpid = -1;

            // --- Select Package ID ---
            while (true) {
                System.out.print("Enter Package ID to book: ");
                String input = sc.nextLine().trim();
                try {
                    tpid = Integer.parseInt(input);
                    if (!isPackageAvailable(conn, tpid)) {
                        System.out.println(" Package ID not found. Try again.");
                        continue;
                    }
                    TourPackageId = tpid;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Enter a numeric Package ID.");
                }
            }

            // --- Select Booking Date ---
            String date;
            LocalDate parsedDate;
            while (true) {
                System.out.print("Enter desired date (YYYY-MM-DD): ");
                date = sc.nextLine().trim();
                try {
                    parsedDate = LocalDate.parse(date);
                    if (parsedDate.isBefore(LocalDate.now())) {
                        System.out.println(" Booking date cannot be in the past.");
                        continue;
                    }

                    checkin_date = parsedDate.plusDays(1);
                    // Check for duplicate booking
                    String checkBooking = "SELECT COUNT(*) FROM booking WHERE tid=? AND tpid=? AND bookingdate=?";
                    try (PreparedStatement pst = conn.prepareStatement(checkBooking)) {
                        pst.setInt(1, tid);
                        pst.setInt(2, tpid);
                        pst.setDate(3, java.sql.Date.valueOf(date));
                        try (ResultSet rs = pst.executeQuery()) {
                            if (rs.next() && rs.getInt(1) > 0) {
                                System.out.println(" You already booked this package on the same date.");
                                continue;
                            }
                        }
                    }
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println(" Invalid date format. Please use YYYY-MM-DD.");
                } catch (SQLException e) {
                    System.out.println(" Database error during booking check: " + e.getMessage());
                    return;
                }
            }

            // --- Get Tour Package Info ---
            try (PreparedStatement pst3 = conn.prepareStatement("SELECT * FROM tourpackage WHERE tpid = ?")) {
                pst3.setInt(1, TourPackageId);
                try (ResultSet rs7 = pst3.executeQuery()) {
                    if (rs7.next()) {
                        tourDay = rs7.getInt("duration");
                        tpName = rs7.getString("title");
                        checkOut_date = parsedDate.plusDays(tourDay - 1);
                    } else {
                        System.out.println(" Tour package not found.");
                        return;
                    }
                }
            }

            // --- Choose Transport ---
            Map<Integer, Transport> transportMap = new HashMap<>();
            try (PreparedStatement pst4 = conn.prepareStatement("SELECT * FROM transport WHERE tpid = ?")) {
                pst4.setInt(1, tpid);
                try (ResultSet rs1 = pst4.executeQuery()) {
                    System.out.println("\n Transport Services:");
                    while (rs1.next()) {
                        int id = rs1.getInt("id");
                        String mode = rs1.getString("mode");
                        String provider = rs1.getString("provider");
                        String contact = rs1.getString("contact");
                        double cost = rs1.getDouble("cost");

                        Transport t = new Transport(id, mode, provider, contact, cost);
                        transportMap.put(id, t);

                        System.out.println(" | ID: " + id);
                        System.out.println(" | Mode: " + mode);
                        System.out.println(" | Provider: " + provider);
                        System.out.println(" | Contact: " + contact);
                        System.out.println(" | Cost: ₹" + cost);
                        System.out.println();
                    }
                }
            }

            if (transportMap.isEmpty()) {
                System.out.println(" No transport options available.");
                return;
            }

            int transportId = -1;
            while (true) {
                System.out.print("Enter Transport ID: ");
                String tInput = sc.nextLine().trim();
                try {
                    transportId = Integer.parseInt(tInput);
                    if (!transportMap.containsKey(transportId)) {
                        System.out.println(" Invalid Transport ID. Try again.");
                        continue;
                    }

                    Transport selected = transportMap.get(transportId);
                    trsnportName = selected.provider;
                    tContact = selected.contact;
                    id = transportId;
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Enter a numeric Transport ID.");
                }
            }

            // --- Hotel Selection ---
            Map<Integer, String> hotelNameMap = new HashMap<>();
            int hId = -1;
            try (PreparedStatement pst2 = conn.prepareStatement("SELECT * FROM hotels WHERE tpid = ?")) {
                pst2.setInt(1, tpid);
                try (ResultSet rs = pst2.executeQuery()) {
                    System.out.println("\n Available Hotels:");
                    System.out.println("----------------------------------------------------------");
                    while (rs.next()) {
                        int hid = rs.getInt("hid");
                        String name = rs.getString("name");
                        String contact = rs.getString("contact");
                        int troom = rs.getInt("troom");
                        int aroom = rs.getInt("aroom");
                        int rating = rs.getInt("rating");
                        String location = rs.getString("location");
                        double price = rs.getDouble("price");

                        System.out.println(" Hotel ID        : " + hid);
                        System.out.println(" Name            : " + name);
                        System.out.println(" Contact         : " + contact);
                        System.out.println(" Location        : " + location);
                        System.out.println(" Rating          : " + rating + " / 5");
                        System.out.println(" Total Rooms     : " + troom);
                        System.out.println(" Available Rooms : " + aroom);
                        System.out.println(" Price per Night : ₹" + price);
                        System.out.println("----------------------------------------------------------");

                        hotelNameMap.put(hid, name);
                    }
                }
            }

            if (hotelNameMap.isEmpty()) {
                System.out.println(" No hotels available.");
                return;
            }

            while (true) {
                System.out.print("Enter Hotel ID: ");
                String input = sc.nextLine().trim();
                try {
                    hId = Integer.parseInt(input);
                    if (!hotelNameMap.containsKey(hId)) {
                        System.out.println(" Invalid Hotel ID. Try again.");
                        continue;
                    }

                    try (PreparedStatement ps = conn.prepareStatement("SELECT price, name, contact FROM hotels WHERE hid = ?")) {
                        ps.setInt(1, hId);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                hotelPrice = rs.getDouble("price");
                                hotelName = rs.getString("name");
                                hContact = rs.getString("contact");
                            } else {
                                System.out.println(" Hotel not found. Try again.");
                                continue;
                            }
                        }
                    }

                    // Decrease available room count
                    try (PreparedStatement pst8 = conn.prepareStatement("UPDATE hotels SET aroom = aroom - 1 WHERE hid = ?")) {
                        pst8.setInt(1, hId);
                        pst8.executeUpdate();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Enter a numeric Hotel ID.");
                } catch (SQLException e) {
                    System.out.println("️ Error selecting hotel: " + e.getMessage());
                }
            }

            // --- Insert Booking ---
            String insertBooking = "INSERT INTO booking(tid, tpid, bookingdate, status, transportid, id, hid, pname, tname, tcon, hname, hcon, hroom, checkinDate, checkoutDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(insertBooking, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, tid);
                ps.setInt(2, tpid);
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(parsedDate, LocalTime.now())));
                ps.setString(4, "booked");
                ps.setInt(5, transportId);
                ps.setInt(6, id);
                ps.setInt(7, hId);
                ps.setString(8, tpName);
                ps.setString(9, trsnportName);
                ps.setString(10, tContact);
                ps.setString(11, hotelName);
                ps.setString(12, hContact);
                ps.setInt(13, room);
                ps.setDate(14, java.sql.Date.valueOf(checkin_date));
                ps.setDate(15, java.sql.Date.valueOf(checkOut_date));

                int r = ps.executeUpdate();
                if (r > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            currentBookingId = rs.getInt(1);
                            currentTpid = tpid;
                            currentTransportId = transportId;
                        }
                    }
                    makePayment(); // Triggers payment logic
                    conn.commit();
                    System.out.println(" Booking and payment completed successfully!");
                } else {
                    System.out.println(" Booking failed.");
                    conn.rollback();
                }
            }

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println(" Rollback failed: " + rollbackEx.getMessage());
            }
            System.out.println(" Booking error: " + e.getMessage());
            e.printStackTrace();
        }
    }



    // Payment of Package
    static void makePayment() {
        try {
            if (currentBookingId == -1 || currentTpid == -1) {
                System.out.println(" No booking found to make payment for.");
                return;
            }

            System.out.println("💳 You have to make a payment.");
            String paymentMode = "";
            while (true) {
                System.out.print("Enter payment mode (Cash/UPI/Card): ");
                paymentMode = sc.nextLine().trim();
                if (paymentMode.equalsIgnoreCase("Cash") ||
                        paymentMode.equalsIgnoreCase("UPI") ||
                        paymentMode.equalsIgnoreCase("Card")) {
                    break;
                }
                System.out.println(" Invalid payment mode. Must be Cash, UPI, or Card.");
            }

            String upiId = null, mobile = null, cvv = null, expiryDate = null, cardNumber = null;

            if (paymentMode.equalsIgnoreCase("UPI")) {
                while (true) {
                    System.out.print("Enter UPI ID: ");
                    upiId = sc.nextLine().trim();
                    if (upiId.contains("@") && upiId.length() >= 8) break;
                    System.out.println(" Invalid UPI ID. Must contain '@' and be at least 8 characters.");
                }

                while (true) {
                    System.out.print("Enter Mobile Number: ");
                    mobile = sc.nextLine().trim();
                    if (mobile.matches("[6789]\\d{9}")) break;
                    System.out.println(" Invalid mobile number. Must be 10 digits and start with 6, 7, 8, or 9.");
                }

            } else if (paymentMode.equalsIgnoreCase("Card")) {
                while (true) {
                    System.out.print("Enter Card Number (12 digits): ");
                    cardNumber = sc.nextLine().trim();
                    if (!cardNumber.matches("\\d{12}")) {
                        System.out.println(" Invalid card number. Must be exactly 12 digits.");
                        continue;
                    }

                    if (cardNumber.matches(".*(\\d)\\1{4,}.*")) {
                        System.out.println(" Invalid card number. Too many repeating digits.");
                        continue;
                    }

                    System.out.print("Enter CVV (3 digits): ");
                    cvv = sc.nextLine().trim();
                    if (!cvv.matches("\\d{3}")) {
                        System.out.println(" Invalid CVV. Must be 3 digits.");
                        continue;
                    }

                    while (true) {
                        System.out.print("Enter Card Expiry Date (MM/YY): ");
                        expiryDate = sc.nextLine().trim();
                        try {
                            YearMonth exp = YearMonth.parse(expiryDate, DateTimeFormatter.ofPattern("MM/yy"));
                            if (exp.isBefore(YearMonth.now())) {
                                System.out.println(" Expiry date is in the past.");
                            } else {
                                break;
                            }
                        } catch (DateTimeParseException e) {
                            System.out.println(" Invalid format. Use MM/YY.");
                        }
                    }

                    break;
                }
            }

            // Fetch Package and Transport Costs
            double packagePrice = 0, transportCost = 0;
            String sql = "SELECT tp.price AS package_price, COALESCE(tr.cost, 0) AS transport_cost FROM booking b JOIN tourpackage tp ON b.tpid = tp.tpid LEFT JOIN transport tr ON b.transportid = tr.id WHERE b.bid = ?";


            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, currentBookingId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        packagePrice = rs.getDouble("package_price");
                        transportCost = rs.getDouble("transport_cost");
                    } else {
                        System.out.println(" Could not retrieve cost details. Payment aborted.");
                        conn.rollback();
                        return;
                    }
                }
            }

            double totalAmount = packagePrice + transportCost + (hotelPrice * tourDay);

            // Insert Payment Record
            String sqlPayment = "INSERT INTO payment (bid, amt, paymentdate, paystatus, mode) VALUES (?, ?, ?, ?, ?)";
            int paymentId = -1;

            try (PreparedStatement psPayment = conn.prepareStatement(sqlPayment, Statement.RETURN_GENERATED_KEYS)) {
                psPayment.setInt(1, currentBookingId);
                psPayment.setDouble(2, totalAmount);
                psPayment.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                psPayment.setString(4, "Paid");
                psPayment.setString(5, paymentMode);

                psPayment.executeUpdate();

                try (ResultSet rsPay = psPayment.getGeneratedKeys()) {
                    if (rsPay.next()) {
                        paymentId = rsPay.getInt(1);
                    }
                }
            }

            // Display Summary
            System.out.println("\n --- Payment Receipt ---");
            System.out.println("Booking ID      : " + currentBookingId);
            System.out.println("Payment ID      : " + paymentId);
            System.out.println("Package Price   : ₹" + packagePrice);
            System.out.println("Transport Cost  : ₹" + transportCost);
            System.out.println("Hotel Cost      : ₹" + (hotelPrice * tourDay));
            System.out.println("Total Amount    : ₹" + totalAmount);
            System.out.println("Payment Mode    : " + paymentMode);

            if (paymentMode.equalsIgnoreCase("UPI")) {
                System.out.println("UPI ID          : " + upiId);
                System.out.println("Mobile          : " + mobile);
            } else if (paymentMode.equalsIgnoreCase("Card")) {
                System.out.println("Card Number     : " + cardNumber);
                System.out.println("CVV             : " + cvv);
                System.out.println("Expiry Date     : " + expiryDate);
            }

            System.out.println("\n Payment Successful. <<< Booking Confirmed >>>");

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("️ Rollback failed: " + rollbackEx.getMessage());
            }
            System.out.println(" Payment error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static boolean isTransportAvailable(Connection conn, int transportId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM transport WHERE id = ?");
        ps.setInt(1, transportId);
        ResultSet rs = ps.executeQuery();
        return rs.next() && rs.getInt(1) > 0;
    }

    public static boolean isPackageAvailable(Connection conn,int tpid) {
        try {
            String sq1 = "SELECT COUNT(*) FROM tourpackage where tpid=?";
            PreparedStatement pst1 = conn.prepareStatement(sq1);
            pst1.setInt(1,tpid);
            ResultSet rs1=pst1.executeQuery();
            while (rs1.next())
            {
                return rs1.getInt(1)>0;
            }
        }catch (SQLException e)
        {
            throw  new RuntimeException();
        }
        return false;
    }
    //--Cancel Booking--
    static void cancelBooking() {
        try {
            // Step 1: Retrieve all bookings of the logged-in user
            PreparedStatement getBookings = conn.prepareStatement(
                    "SELECT b.bid,b.tpid, b.bookingdate, b.status, tp.title " +
                            "FROM booking b " +
                            "JOIN tourpackage tp ON b.tpid = tp.tpid " +
                            "WHERE b.tid = ? AND b.status = 'booked'"
            );
            getBookings.setInt(1, loggedInTid);
            ResultSet rs = getBookings.executeQuery();

            List<Integer> validBookingIds = new ArrayList<>();
            System.out.println("\nYour Active Bookings:");
            boolean hasBooking = false;

            while (rs.next()) {
                hasBooking = true;
                int bid = rs.getInt("bid");
                Timestamp bookingDate = rs.getTimestamp("bookingdate");
                String status = rs.getString("status");
                String pname = rs.getString("title");  // fixed
                int tpid = rs.getInt("tpid");
                validBookingIds.add(bid);

                System.out.println("\n | Booking ID : " + bid +
                                   "\n | Package ID : " + tpid +
                                   "\n | Package    : " + pname +
                                   "\n | Date       : " + bookingDate.toLocalDateTime().toLocalDate() +
                                   "\n | Status     : " + status);
                System.out.println("----------------------------");
            }

            if (!hasBooking) {
                System.out.println("No active bookings found.");
                return;
            }

            // Step 2: Prompt user to enter booking ID
            int bid = -1;
            while (true) {
                System.out.print("\nEnter Booking ID to cancel: ");
                if (sc.hasNextInt()) {
                    bid = sc.nextInt();
                    sc.nextLine(); // consume newline
                    if (validBookingIds.contains(bid)) {
                        break;
                    } else {
                        System.out.println("Invalid Booking ID. Please select one from your active bookings.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer Booking ID.");
                    sc.nextLine(); // consume invalid input
                }
            }

            // Step 3: Call procedure to cancel booking
            String sql = "{CALL cancelBooking(?)}";
            CallableStatement cst = conn.prepareCall(sql);
            cst.setInt(1, bid);
            int r = cst.executeUpdate();

            if (r > 0) {
                System.out.println("Booking cancelled successfully!");
                System.out.println("Package Amount Credited In Your Account Soon ..");
            } else {
                System.out.println("Failed to cancel booking.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    //--View Transports--
    static void viewTransports() {
        System.out.println("\n Available Transport Options:");
        for (Transport t : transportMap.values())
        {
            System.out.println(" | ID: " + t.id + "\n | " + t.mode + "\n | Provider: " + t.provider +
                    "\n | Contact: " + t.contact + "\n | ₹" + t.cost);
            System.out.println("---------------------");
        }
    }

    static void exportBookings() {
        String filename = "booking_report_tourist" + loggedInTid + ".txt";


        try (PrintWriter pw = new PrintWriter(filename);
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM booking WHERE tid = ?")) {

            ps.setInt(1, loggedInTid);
            try (ResultSet rs = ps.executeQuery()) {
                pw.println("----- Bookings for Tourist ID: " + loggedInTid + " -----");
                System.out.println("      Your Bookings      ");
                while (rs.next()) {
                    int bid = rs.getInt("bid");
                    int pid = rs.getInt("tpid");
                    String date = rs.getString("bookingdate");
                    String status = rs.getString("status");
                    String pname = rs.getString("pname");
                    String tname = rs.getString("tname");
                    String tcon = rs.getString("tcon");
                    String hname = rs.getString("hname");
                    String hcon = rs.getString("hcon");
                    int room = rs.getInt("hroom");
                    Date checkIn = rs.getDate("checkinDate");
                    Date checkOut = rs.getDate("checkoutDate");

                    // Console output
                    System.out.println("\n  Booking ID       : " + bid);
                    System.out.println("  Package ID       : " + pid);
                    System.out.println("  Place Name       : " +pname);
                    System.out.println("  Tour Date        : " + date);
                    System.out.println("  Transport Name   : " + tname);
                    System.out.println("  Tranpoer Contact :" + tcon);
                    System.out.println("  Hotel Name       :" + hname);
                    System.out.println("  Hotel Contact    :" + hcon);
                    System.out.println("  Hotel Room       :" + room);
                    System.out.println("  check in date    :" + checkIn);
                    System.out.println("  check out date   :" + checkOut);
                    System.out.println("  Status           : " + status);
                    System.out.println("----------------------------------");

                    // File output
                    pw.println("  Booking ID        : " + bid);
                    pw.println("  Package ID        : " + pid);
                    pw.println("  Place Name        : " +pname);
                    pw.println("  Tour Date         : " + date);
                    pw.println("  Transport Name    : " + tname);
                    pw.println("  Transport Contact :" + tcon);
                    pw.println("  Hotel Name        :" + hname);
                    pw.println("  Hotel Contact     :" + hcon);
                    pw.println("  Hotel Room        :" + room);
                    pw.println("  check in date     :" + checkIn);
                    pw.println("  check out date    :" + checkOut);
                    pw.println("  Status            : " + status);
                    pw.println("-------------------------------------");
                }
            }

            System.out.println("Booking history exported to: " + filename);
        } catch (Exception e) {
            System.err.println("Export error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    static void reviewPackage() {
        System.out.println("\n--- Your Booked Packages ---");
        List<Integer> bookedTpidList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT b.tpid, tp.title " +
                    "FROM booking b " +
                    "JOIN tourpackage tp ON b.tpid = tp.tpid " +
                    "WHERE b.tid = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loggedInTid);
            ResultSet rs = ps.executeQuery();

            int count = 0;
            while (rs.next()) {
                int tpid = rs.getInt("tpid");
                String title = rs.getString("title");
                System.out.println("Package ID: " + tpid + " | Title: " + title);
                bookedTpidList.add(tpid);
                count++;
            }

            if (count == 0) {
                System.out.println(" You have not booked any packages. No reviews allowed.");
                return;
            }
        } catch (SQLException e) {
            System.out.println(" Error fetching booked packages: " + e.getMessage());
            return;
        }

        int selectedTpid = -1;
        while (true) {
            System.out.print("Enter Package ID to review: ");
            String input = sc.nextLine();
            try {
                selectedTpid = Integer.parseInt(input);
                if (!bookedTpidList.contains(selectedTpid)) {
                    System.out.println(" You can only review a package you have booked.");
                    continue;
                }

                // Check for existing review
                String checkSql = "SELECT COUNT(*) FROM review WHERE tid = ? AND tpid = ?";
                PreparedStatement psCheck = conn.prepareStatement(checkSql);
                psCheck.setInt(1, loggedInTid);
                psCheck.setInt(2, selectedTpid);
                ResultSet rsCheck = psCheck.executeQuery();

                if (rsCheck.next() && rsCheck.getInt(1) > 0) {
                    System.out.println(" You have already reviewed this package.");
                    return;
                }

                break; // Valid selection
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a valid numeric Package ID.");
            } catch (SQLException e) {
                System.out.println(" Error checking existing reviews: " + e.getMessage());
                return;
            }
        }

        int rating = 0;
        while (true) {
            System.out.print("Enter Rating (1 to 5 stars): ");
            String input = sc.nextLine();
            try {
                rating = Integer.parseInt(input);
                if (rating < 1 || rating > 5) {
                    System.out.println(" Rating must be between 1 and 5.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println(" Invalid input. Please enter a numeric rating.");
            }
        }

        String message = "";
        while (true) {
            System.out.print("Enter your review message: ");
            message = sc.nextLine().trim();
            if (message.isEmpty()) {
                System.out.println(" Review message cannot be empty.");
                continue;
            }
            break;
        }

        try {
            String insertSql = "INSERT INTO review (tid, tpid, rating, message, reviewdate) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(insertSql);
            ps.setInt(1, loggedInTid);
            ps.setInt(2, selectedTpid);
            ps.setInt(3, rating);
            ps.setString(4, message);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println(" Review submitted successfully.");
            } else {
                System.out.println(" Failed to submit review.");
            }
        } catch (SQLException e) {
            System.out.println(" Error while inserting review: " + e.getMessage());
        }
    }
    //--Admin Panel--
    static void adminMenu() throws SQLException {
        while (true) {
            System.out.println("\n --- Admin Panel ---");
            System.out.println("1. View All Tourists");
            System.out.println("2. View All Tour Package");
            System.out.println("3. Add Tour Package");
            System.out.println("4. Remove Tour Package");
            System.out.println("5. View All Bookings");
            System.out.println("6. View Payments");
            System.out.println("7. View Canceled booking");
            System.out.println("8. View Transport List");
            System.out.println("9. Add Transport Option");
            System.out.println("10. Remove Transport Option");
            System.out.println("11. Add Hotels");
            System.out.println("12. Logout");
            int ch = -1;
            while (true) {
                System.out.print("Enter your choice : ");
                if (sc.hasNextInt()) {
                    ch = sc.nextInt();
                    if (ch >= 1 && ch <= 12) {
                        break;
                    } else {
                        System.out.println("Invalid option. Please enter a number between 1 and 11.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
            }
            switch (ch) {
                case 1 -> viewAllTourists();
                case 2 -> viewAllTourpacke();
                case 3 -> addTourPackage();
                case 4 -> removeTourPackage();
                case 5 -> viewAllBookings();
                case 6 -> viewAllPayments();
                case 7 -> viewCancelLogs();
                case 8 -> viewAllTransport();
                case 9 -> addTransport();
                case 10 ->deleteTransport();
                case 11 ->addHotels();
                case 12 -> { return; }

                default -> System.out.println(" Invalid option.");
            }
        }
    }
    //--Tourist List--
    static void viewAllTourists() {
        try {
            String sql="{call viewAllTourists()}";
            CallableStatement cst=conn.prepareCall(sql);
            ResultSet rs=cst.executeQuery();
            System.out.println("\n ----- Tourist Information ----- \n");
            while (rs.next()) {
                System.out.print("\n| TouristID: " + rs.getInt(1) +
                        "\n| Name : " +rs.getString(2) +
                        "\n| Gender : " + rs.getString(3) +
                        "\n| Address : " + rs.getString(4) +
                        "\n| Email : " + rs.getString(5) +
                        "\n| Contact No : " + rs.getString(6) +
                        "\n| Password : " +rs.getString(7) +
                        "\n| Age : " + rs.getInt(8));
                System.out.println("\n---------------------------------");
            }
        }catch (Exception e)
        {
            System.out.println("Error: " + e);
        }
    }
    //--Add Tour Package--
    static void addTourPackage() {
        try {
            System.out.print("Enter package Name: ");
            String title = sc.nextLine();

            int duration = 0;
            while (true) {
                System.out.print("Enter duration (days): ");
                if (sc.hasNextInt()) {
                    duration = sc.nextInt();
                    if (duration > 0) {
                        sc.nextLine(); // consume newline
                        break;
                    } else {
                        System.out.println("Duration must be positive.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    sc.next();
                }
            }

            double price = 0;
            while (true) {
                System.out.print("Enter price: ");
                if (sc.hasNextDouble()) {
                    price = sc.nextDouble();
                    if (price >= 0) {
                        sc.nextLine(); // consume newline
                        break;
                    } else {
                        System.out.println("Price cannot be negative.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.next();
                }
            }

            System.out.print("Image file path (optional): ");
            String imagePath = sc.nextLine();

            FileInputStream fis = null;
            if (!imagePath.isBlank()) {
                File f = new File(imagePath);
                if (!f.exists() || !f.isFile()) {
                    System.out.println("Image file does not exist or is invalid. Proceeding without image.");
                } else {
                    fis = new FileInputStream(f);
                }
            }

            String sql = "{call addTourPackage(?,?,?,?)}";
            CallableStatement cst = conn.prepareCall(sql);
            cst.setString(1, title);
            cst.setInt(2, duration);
            cst.setDouble(3, price);

            if (fis != null) {
                cst.setBlob(4, fis);
            } else {
                cst.setNull(4, java.sql.Types.BLOB);
            }

            int r = cst.executeUpdate();
            System.out.println((r > 0) ? "Package Added Successfully..." : "Failed to add package.");

            if (fis != null) {
                fis.close();
            }

        } catch (Exception e) {
            System.out.println("Error adding package: " + e.getMessage());
        }
    }

    static boolean isValidDate(String dateStr) {
        try {
            java.time.LocalDate.parse(dateStr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void viewAllTourpacke() {
        try {
            String sql = "{Call viewAllTourpacke()}";
            CallableStatement cst = conn.prepareCall(sql);
            ResultSet rs = cst.executeQuery();

            System.out.println("\n -------- Tour Packages ---------");
            System.out.println("From Ahmedabad");

            while (rs.next()) {
                System.out.println("Package ID: " + rs.getInt("tpid"));
                System.out.println("Title     : " + rs.getString("title"));
                System.out.println("Duration  : " + rs.getInt("duration") + " days");
                System.out.println("Price     : ₹" + rs.getDouble("price"));

                Blob b = rs.getBlob("image"); // Use column name instead of index
                if (b != null && b.length() > 0) {
                    byte[] ba = b.getBytes(1, (int) b.length());
                    FileOutputStream fos = new FileOutputStream("image" + loggedInTid + ".jpg");
                    fos.write(ba);
                    fos.close();
                    System.out.println("Image     : Saved as image" + loggedInTid + ".jpg");
                } else {
                    System.out.println("Image     : No image available");
                }

                System.out.println("---------------------------");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //--Remove Tour package--
    static void removeTourPackage() {
        while (true) {
            try {
                System.out.print("Enter Package ID to delete : ");
                if (sc.hasNextInt()) {
                    int id = sc.nextInt();
                    sc.nextLine();

                    String sql1 = "{Call removeTourPackage(?)}";
                    CallableStatement cst = conn.prepareCall(sql1);
                    cst.setInt(1, id);
                    int r = cst.executeUpdate();

                    if (r > 0) {
                        packageMap.remove(id);
                        System.out.println("Package removed.");
                    } else {
                        System.out.println(" Package not found.");
                    }
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid integer Package ID.");
                    sc.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Delete error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }

    //--view Booking--
    static void viewAllBookings() {
        try {
            String sql2="{Call viewAllBookings()}";
            CallableStatement cst1=conn.prepareCall(sql2);
            ResultSet rs=cst1.executeQuery();
            System.out.println("\n All Bookings:");
            while (rs.next())
            {
                System.out.println(" | Booking ID: " + rs.getInt("bid")) ;
                System.out.println(" | Tourist ID: " + rs.getInt("tid"));
                System.out.println(" | Package ID: " + rs.getInt("tpid") );
                System.out.println(" | Date: " + rs.getString("bookingdate"));
                System.out.println(" | Status: " + rs.getString("status"))  ;
                System.out.println();
            }
        }
        catch (Exception e) {
            System.out.println(" Booking view error: " + e);
        }
    }
    //-- view Payment--
    static void viewAllPayments() {
        try {
            String sql3="{Call viewAllPayments()}";
            CallableStatement cst3=conn.prepareCall(sql3);
            ResultSet rs=cst3.executeQuery();
            System.out.println("\n Payments:");
            while (rs.next()) {
                System.out.println(" | Payment ID: " + rs.getInt("pid"));
                System.out.println(" | Booking ID: " + rs.getInt("bid"));
                System.out.println(" | Payment : ₹" + rs.getDouble("amt"));
                System.out.println(" | Payment Date: " + rs.getString("paymentDate") );
                System.out.println(" | Status: " + rs.getString("paystatus"));
                System.out.println(" | Mode: " + rs.getString("mode"));
                System.out.println();
            }
        }
        catch (Exception e) {
            System.out.println(" Payment view error: " + e);
        }
    }
    static void viewCancelLogs() {
        try {
            String sql4="{Call viewCancelLogs()}";
            CallableStatement cst3=conn.prepareCall(sql4);
            ResultSet rs=cst3.executeQuery();
            System.out.println("\n Cancelled Bookings:");
            boolean found = false;
            while (rs.next()) {
                System.out.println(" | Booking ID: " + rs.getInt("bid"));
                System.out.println(" | Tourist ID: " + rs.getInt("tid"));
                System.out.println(" | Package ID: " + rs.getInt("tpid"));
                System.out.println(" | Date: " + rs.getTimestamp("text_date"));
                System.out.println(" | Status: Cancelled");
                System.out.println();
                found = true;
            }
            if (!found) {
                System.out.println("No cancellations.");
            }
        } catch (Exception e) {
            System.out.println("Error viewing cancelled bookings: " + e);
        }
    }
    //--view Transport List--
    static void viewAllTransport() throws SQLException {
        String sql5 = "{Call viewAllTransport()}";
        CallableStatement cst4 = conn.prepareCall(sql5);
        ResultSet rs = cst4.executeQuery();

        System.out.println("\nTransport Services:");

        // Clear outdated entries
        transportMap.clear();

        // Populate transportMap with fresh data
        while (rs.next()) {
            int id = rs.getInt("id");
            String mode = rs.getString("mode");
            String provider = rs.getString("provider");
            String contact = rs.getString("contact");
            double cost = rs.getDouble("cost");

            Transport t = new Transport(id, mode, provider, contact, cost);
            transportMap.put(id, t);
        }
        // Display current transport options
        if (transportMap.isEmpty()) {
            System.out.println("No transport options found.");
        } else {
            for (Transport t : transportMap.values()) {
                System.out.println(" | ID: " + t.id);
                System.out.println(" | Mode: " + t.mode);
                System.out.println(" | Provider: " + t.provider);
                System.out.println(" | Contact: " + t.contact);
                System.out.println(" | Cost: ₹" + t.cost);
                System.out.println();
            }
        }
    }

    //--View Transport--
    static void addTransport() {
        viewPackages();
        try {
            sc.nextLine();
            System.out.println(".....Select Package For Transpot .....");

            int tpid = -1;

            // --- Select Package ID ---
            while (true) {
                System.out.print("Enter Package ID For Tranport: ");
                String input = sc.nextLine();
                try {
                    tpid = Integer.parseInt(input);
                    if (!isPackageAvailable(conn, tpid)) {
                        System.out.println(" Package ID not found. Try again.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Enter a valid numeric Package ID.");
                }
            }
            String mode;
            while (true) {
                System.out.print("Mode (Bus/Train/Car/Flight): ");
                mode = sc.nextLine();
                if (mode.equalsIgnoreCase("Bus") || mode.equalsIgnoreCase("Train") || mode.equalsIgnoreCase("Car") || mode.equalsIgnoreCase("Flight")) {
                    break;
                } else {
                    System.out.println("Invalid mode. Please enter Bus, Train, Car, or Flight.");
                }
            }

            System.out.print("Provider Name: ");
            String provider = sc.nextLine();

            String contact;
            while (true) {
                System.out.print("ContactNo starts with(9/8/7/6): ");
                contact = sc.nextLine();
                if (contact.length() == 10 && (contact.startsWith("9") || contact.startsWith("8") || contact.startsWith("7") || contact.startsWith("6"))) {
                    break;
                } else {
                    System.out.println("Invalid Contact Number. Must be 10 digits and start with 9, 8, 7, or 6.");
                }
            }

            double cost;
            while (true) {
                System.out.print("Cost: ₹");
                if (sc.hasNextDouble()) {
                    cost = sc.nextDouble();
                    sc.nextLine();
                    if (cost > 0) {
                        break;
                    } else {
                        System.out.println("Invalid cost. Must be greater than 0.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number for cost.");
                    sc.nextLine();
                }
            }

            String sql6 = "{Call addTransport(?,?,?,?,?)}";
            CallableStatement cst5 = conn.prepareCall(sql6);
            cst5.setString(1, mode);
            cst5.setString(2, provider);
            cst5.setString(3, contact);
            cst5.setDouble(4, cost);
            cst5.setInt(5, tpid);
            cst5.executeUpdate();

            System.out.println("Transport option added...");
        } catch (Exception e) {
            System.out.println("Add transport error: " + e.getMessage());
        }
    }

    static void deleteTransport() {
        int tid = -1;
        while (true) {
            System.out.print("Enter Transport ID to Delete: ");
            if (sc.hasNextInt()) {
                tid = sc.nextInt();
                if (tid > 0) {
                    break;
                } else {
                    System.out.println(" Please enter a positive integer ID.");
                }
            } else {
                System.out.println(" Invalid input. Please enter a valid integer.");
                sc.next();
            }
        }

        String sql = "{call deleteTransport(?)}";
        try {
            CallableStatement cst = conn.prepareCall(sql);
            cst.setInt(1, tid);
            int r = cst.executeUpdate();
            if (r > 0) {
                System.out.println(" Transport with ID " + tid + " deleted successfully.");
            } else {
                System.out.println(" No transport record found for ID " + tid + ".");
            }
        } catch (SQLException e) {
            System.err.println(" Error deleting transport: " + e.getMessage());
            e.printStackTrace();
        }

    }
    static void addHotels() {
        System.out.println(".....Select Package For Hotel .....");
        viewPackages();
        sc.nextLine();
        try {
            int tpid = -1;
            while (true) {
                try {
                    System.out.print("Enter Package ID linked to this hotel: ");

                    tpid = Integer.parseInt(sc.nextLine());
                    if (!isPackageAvailable(conn, tpid)) {
                        System.out.println(" Package ID does not exist. Please try again.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Please enter a numeric Package ID.");
                }
            }

            System.out.print("Enter Hotel Name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(" Hotel name cannot be empty.");
                return;
            }

            String contact;
            while (true) {
                System.out.print("ContactNo starts with(9/8/7/6): ");
                contact = sc.nextLine();
                if (contact.length() == 10 && (contact.startsWith("9") || contact.startsWith("8") || contact.startsWith("7") || contact.startsWith("6"))) {
                    break;
                } else {
                    System.out.println("Invalid Contact Number. Must be 10 digits and start with 9, 8, 7, or 6.");
                }
            }

            System.out.print("Enter Location: ");
            String location = sc.nextLine().trim();
            if (location.isEmpty()) {
                System.out.println(" Location cannot be empty.");
                return;
            }

            int totalRooms = -1;
            while (true) {
                try {
                    System.out.print("Enter Total Rooms: ");
                    totalRooms = Integer.parseInt(sc.nextLine());
                    if (totalRooms <= 0) {
                        System.out.println(" Total rooms must be greater than 0.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Please enter a valid number for total rooms.");
                }
            }

            int availableRooms = -1;
            while (true) {
                try {
                    System.out.print("Enter Available Rooms: ");
                    availableRooms = Integer.parseInt(sc.nextLine());
                    if (availableRooms < 0 || availableRooms > totalRooms) {
                        System.out.println(" Available rooms must be between 0 and " + totalRooms + ".");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Please enter a valid number for available rooms.");
                }
            }
            int rating = -1;
            while (true) {
                try {
                    System.out.print("Enter Hotel Rating (1-5): ");
                    rating = Integer.parseInt(sc.nextLine());
                    if (rating < 1 || rating > 5) {
                        System.out.println(" Rating must be between 1 and 5.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Please enter a number between 1 and 5.");
                }
            }
            double price;
            while (true) {
                try {
                    System.out.print("Enter Price per Night (₹): ");
                    price = Double.parseDouble(sc.nextLine().trim());
                    if (price < 0) {
                        System.out.println(" Price cannot be negative.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" Invalid input. Enter a valid price.");
                }
            }

            String sql = "INSERT INTO hotels (tpid, name, contact, location, troom, aroom, rating, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, tpid);
            pst.setString(2, name);
            pst.setString(3, contact);
            pst.setString(4, location);
            pst.setInt(5, totalRooms);
            pst.setInt(6, availableRooms);
            pst.setInt(7, rating);
            pst.setDouble(8,price);

            int rowsInserted = pst.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println(" Hotel added successfully!");
            } else {
                System.out.println(" Failed to add hotel.");
            }
        } catch (SQLException e) {
            System.out.println(" SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(" Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}