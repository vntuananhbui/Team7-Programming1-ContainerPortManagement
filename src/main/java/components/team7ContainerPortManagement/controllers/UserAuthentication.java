package src.main.java.components.team7ContainerPortManagement.controllers;

public class UserAuthentication {
//    public static List<User> readUserCredentialsFromFile(String filename) throws IOException {
//        List<User> credentials = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                if (parts.length == 4) {
//                    String U_ID = parts[0].trim();
//                    String username = parts[1].trim();
//                    String hashedpassword = parts[2].trim();
//                    String userType = parts[3].trim();
//                    credentials.add(new User(U_ID,username,hashedpassword,userType));
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading credentials file: " + e.getMessage());
//        }
//        return credentials;
//    }

//    public static boolean checkCredentials(String username, String password) {
//            boolean isAuthentication = false;
//            try {
//                Scanner fileScanner = new Scanner(new File("./src/main/java/resources/data/user.txt"));
//
//                while (fileScanner.hasNext()) {
//                    String line = fileScanner.nextLine();
//                    String[] values = line.split(","); // Split each value in each line by comma
//                    if (values[1].equals(username))
//                    // If the username already had in customer file continue to check the password
//                    {
//                        if (values[2].equals((User.hashing(password))))
////                    if (values[2].equals(password))
////                    // If the password match, it will return true
//                        {
//                            isAuthentication = true;
//                        }
//                    }
//                }
//            } catch (FileNotFoundException fe) {
//                fe.printStackTrace();
//            }
//        return isAuthentication;
//    }
//
//    public static String getUserType(String username) {
//        try {
//            Scanner fileScanner = new Scanner(new File("./src/main/java/resources/data/user.txt"));
//            while (fileScanner.hasNext()) {
//                String line = fileScanner.nextLine();
//                String[] values = line.split(",");
//                if (values[1].equals(username)) {
//                    return values[3]; // Return the user type
//                }
//            }
//        } catch (FileNotFoundException fe) {
//            fe.printStackTrace();
//        }
//        return null;
//    }

//    public static void registerUser(List<User> userList, String username, String password, String userType) {
//        Random randNum = new Random();
//        int U_ID = randNum.nextInt(9999);
//        String nextUid = "U" + U_ID;
//        userList.add(new User(nextUid, username, password,userType));
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/resources/data/user.txt", true))) {
//            User newUser = userList.get(userList.size() - 1);
//            writer.write(newUser.getU_ID() + "," + newUser.getUsername() + "," + newUser.getPassword() + "," + newUser.getUserType());
//            writer.newLine();
//            System.out.println("Registration successful!");
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//}
    }



