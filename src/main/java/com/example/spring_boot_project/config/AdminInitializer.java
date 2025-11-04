package com.example.spring_boot_project.config;

//import com.example.spring_boot_project.model.User;
//import com.example.spring_boot_project.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;


// ----- fix later

//@Component
//public class AdminInitializer implements CommandLineRunner {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) {
//        String tempAdminUsername = System.getenv("ADMIN_USERNAME");
//        if (tempAdminUsername == null) {
//            tempAdminUsername = System.getProperty("ADMIN_USERNAME");
//        }
//
//        String tempAdminPassword = System.getenv("ADMIN_PASSWORD");
//        if (tempAdminPassword == null) {
//            tempAdminPassword = System.getProperty("ADMIN_PASSWORD");
//        }
//
//        if (tempAdminUsername == null || tempAdminPassword == null) {
//            System.err.println("⚠️ ADMIN_USERNAME and ADMIN_PASSWORD are not set. Admin will not be created.");
//            return;
//        }
//
//        final String adminUsername = tempAdminUsername;
//        final String adminPassword = tempAdminPassword;
//
//        userRepository.findByUsername(adminUsername).ifPresentOrElse(
//            user -> System.out.println(),
//            () -> {
//                User admin = new User();
//                admin.setUsername(adminUsername);
//                admin.setPassword(passwordEncoder.encode(adminPassword));
//                admin.setRole("ADMIN");
//                userRepository.save(admin);
//                //System.out.println("✅ Admin user created successfully");
//            }
//        );
//    }
//}
