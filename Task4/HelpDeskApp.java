import java.util.*;

enum TicketStatus {
    OPEN, ASSIGNED, IN_PROGRESS, RESOLVED, CLOSED
}

class User {
    int id;
    String username;
    String password;
    boolean isSupport;
    boolean isAdmin;
    static int idCounter = 1;

    public User(String username, String password, boolean isSupport, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isSupport = isSupport;
        this.isAdmin = isAdmin;
        this.id = idCounter++;
    }
}

class Ticket {
    int id;
    String subject;
    String description;
    TicketStatus status;
    int userId;
    Integer supportStaffId; // nullable
    String resolution;
    static int idCounter = 1;

    public Ticket(String subject, String description, int userId) {
        this.id = idCounter++;
        this.subject = subject;
        this.description = description;
        this.status = TicketStatus.OPEN;
        this.userId = userId;
        this.resolution = "";
    }
}

class HelpDeskSystem {
    private List<User> users = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private User currentUser = null;

    // User Management
    public User register(String username, String password, boolean isSupport, boolean isAdmin) {
        User user = new User(username, password, isSupport, isAdmin);
        users.add(user);
        return user;
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                currentUser = user;
                System.out.println("Login successful. Welcome, " + user.username + "!\n");
                return true;
            }
        }
        System.out.println("Invalid credentials.\n");
        return false;
    }
    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Ticket Management
    public void submitTicket(String subject, String description) {
        Ticket ticket = new Ticket(subject, description, currentUser.id);
        tickets.add(ticket);
        System.out.println("Ticket submitted with ID: " + ticket.id);
    }

    public List<Ticket> getUserTickets() {
        List<Ticket> result = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.userId == currentUser.id) result.add(t);
        }
        return result;
    }

    // Support Staff
    public List<Ticket> getAssignedTickets() {
        List<Ticket> result = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.supportStaffId != null && t.supportStaffId == currentUser.id) result.add(t);
        }
        return result;
    }

    public List<Ticket> getUnassignedTickets() {
        List<Ticket> result = new ArrayList<>();
        for (Ticket t : tickets) if (t.status == TicketStatus.OPEN) result.add(t);
        return result;
    }

    public void assignTicket(int ticketId) {
        for (Ticket t : tickets) {
            if (t.id == ticketId && t.status == TicketStatus.OPEN) {
                t.status = TicketStatus.ASSIGNED;
                t.supportStaffId = currentUser.id;
                System.out.println("Ticket " + ticketId + " assigned.");
                return;
            }
        }
        System.out.println("Ticket not found or already assigned.");
    }

    public void resolveTicket(int ticketId, String resolution) {
        for (Ticket t : tickets) {
            if (t.id == ticketId && t.supportStaffId != null && t.supportStaffId == currentUser.id) {
                t.resolution = resolution;
                t.status = TicketStatus.RESOLVED;
                System.out.println("Ticket " + ticketId + " marked as RESOLVED.");
                return;
            }
        }
        System.out.println("Ticket not found or not assigned to you.");
    }

    // Admin
    public List<User> getAllUsers() { return users; }
    public List<Ticket> getAllTickets() { return tickets; }

    public void printTicketList(List<Ticket> list) {
        for (Ticket t : list) {
            System.out.println("ID: " + t.id + " | Subject: " + t.subject + " | Status: " + t.status +
                    " | Assigned to: " + (t.supportStaffId != null ? t.supportStaffId : "-"));
        }
    }
}

public class HelpDeskApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HelpDeskSystem system = new HelpDeskSystem();

        // Preload admin and support staff
        User admin = system.register("admin", "admin", false, true);
        User support = system.register("staff", "staff", true, false);
        User demoUser = system.register("user", "user", false, false);

        while (true) {
            if (system.getCurrentUser() == null) {
                System.out.println("1. Register 2. Login 3. Exit");
                int ch = sc.nextInt();
                sc.nextLine();
                if (ch == 1) {
                    System.out.print("Username: "); String user = sc.nextLine();
                    System.out.print("Password: "); String pass = sc.nextLine();
                    system.register(user, pass, false, false);
                } else if (ch == 2) {
                    System.out.print("Username: "); String user = sc.nextLine();
                    System.out.print("Password: "); String pass = sc.nextLine();
                    system.login(user, pass);
                } else break;
            } else {
                User curr = system.getCurrentUser();
                if (curr.isAdmin) {
                    System.out.println("Admin Panel: 1. All Users 2. All Tickets 3. Logout");
                    int ch = sc.nextInt();
                    if (ch == 1) {
                        for (User u : system.getAllUsers())
                            System.out.println("ID: "+u.id+" | "+u.username+" | Support:"+u.isSupport+" | Admin:"+u.isAdmin);
                    } else if (ch == 2) {
                        system.printTicketList(system.getAllTickets());
                    } else { system.logout(); }
                } else if (curr.isSupport) {
                    System.out.println("Support Panel: 1. View My Tickets 2. View Unassigned 3. Assign 4. Resolve 5. Logout");
                    int ch = sc.nextInt();
                    sc.nextLine();
                    if (ch == 1)
                        system.printTicketList(system.getAssignedTickets());
                    else if (ch == 2)
                        system.printTicketList(system.getUnassignedTickets());
                    else if (ch == 3) {
                        System.out.print("Ticket ID to assign: "); int tid = sc.nextInt();
                        system.assignTicket(tid);
                    }
                    else if (ch == 4) {
                        System.out.print("Ticket ID to resolve: "); int tid = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Resolution details: "); String res = sc.nextLine();
                        system.resolveTicket(tid, res);
                    }
                    else system.logout();
                } else {
                    System.out.println("User Panel: 1. Submit Ticket 2. View My Tickets 3. Logout");
                    int ch = sc.nextInt();
                    sc.nextLine();
                    if (ch == 1) {
                        System.out.print("Subject: "); String subj = sc.nextLine();
                        System.out.print("Description: "); String desc = sc.nextLine();
                        system.submitTicket(subj, desc);
                    }
                    else if (ch == 2)
                        system.printTicketList(system.getUserTickets());
                    else system.logout();
                }
            }
        }
        sc.close();
    }
}
