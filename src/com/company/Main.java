package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


        class Application {
            public static void main(String[] args) {
                // Generate 50 users
                ArrayList<User> userList = generateUsers(50);

                // Correct status for users
                userList = userList_checkAndSetCorrectStatus(userList);

                // Print all users
                for (int i = 0; i < userList.size(); i++) {
                    userList.get(i).printDetails("dd.MM.yyyy");
                }
            }

            // Methods necessary for automation and checking

            /**
             * This method generates a list of users. All new users are marked as new at
             * first.
             *
             * @param usersAmount
             * @return ArrayList<User>
             */
            private static ArrayList<User> generateUsers(int usersAmount) {
                ArrayList<User> userList = new ArrayList<User>();

                // Create x amount of new users. Each user will have a different detail.
                for (int i = 0; i < usersAmount; i++) {
                    User newUser = new User("firstName_" + i, "lastName_" + i, "email_" + i + "@mail.com", i,
                            substractDateDay(new Date(), i), Status.NEW);
                    userList.add(newUser);
                }

                return userList;
            }

            /**
             * Removes x days from a date.
             *
             * @param dt
             * @param substractDays
             * @return Date
             */
            private static Date substractDateDay(Date dt, int substractDays) {
                // Adds days to today
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DATE, -substractDays);
                return c.getTime();
            }

            /**
             * Check & Correct user status in a list
             *
             * @param userList
             * @return ArrayList<User>
             */
            private static ArrayList<User> userList_checkAndSetCorrectStatus(ArrayList<User> userList) {
                for (int i = 0; i < userList.size(); i++) {
                    User user = checkUserStatus(userList.get(i));

                    // Correct records in the list
                    userList.set(i, user);
                }

                return userList;
            }

            /**
             * Checks and sets correct status for user. Timestamp older than yesterday is
             * marked as Active. Timestamp older than 15 days is marked as Inactive.
             * Timestamp older than one month & Inactive (31 days) is marked as Blocked.
             *
             * @param user
             * @return
             */
            private static User checkUserStatus(User user) {
                Date registrationDate = user.getRegistrationDate();
                Date today = new Date();

                int dayDifference = getDayDateDifference(registrationDate, today);

                // Timestamp older than yesterday is marked as Active
                if (dayDifference > 1 && dayDifference <= 15) {
                    user.setStatus(Status.ACTIVE);
                }

                // Timestamp older than 15 days is marked as Inactive
                else if (dayDifference > 15) {
                    user.setStatus(Status.INACTIVE);
                }

                // Timestamp older than one month & Inactive (31 days) is marked as Blocked.
                if (getDayDateDifference(registrationDate, today) > 31 && user.getStatus() == Status.INACTIVE) {
                    user.setStatus(Status.BLOCKED);
                }

                return user;
            }

            /**
             * Calculates the difference in days between two dates
             *
             * @param date1
             * @param date2
             * @return int
             */
            private static int getDayDateDifference(Date date1, Date date2) {
                Integer dayDifference = 0;
                try {
                    long difference = date2.getTime() - date1.getTime();
                    float daysBetween = (difference / (1000 * 60 * 60 * 24));
                    dayDifference = (int) Math.round(daysBetween);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return dayDifference;
            }
        }

/**
 * This class contains information about the user
 *
 */
        class User {// Am creat class ,care contine cimpuri privat
            private String firstName, lastName, email;
            private int age;
            private Date registrationDate;
            private Status status;

            /**
             * All parameters must be filled in order to instantiate this class
             *
             * @param firstName
             * @param lastName
             * @param email
             * @param age
             * @param registrationDate
             * @param status
             */
            public User(String firstName, String lastName, String email, int age, Date registrationDate, Status status) {
                super();
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.age = age;
                this.registrationDate = registrationDate;
                this.status = status;
            }

            // Custom methods
            /**
             * Returns a string from date in a specific format
             *
             * @param date
             * @param formatDate
             * @return String
             */
            public static String DateToString(Date date, String formatDate) {
                DateFormat dateFormat = new SimpleDateFormat(formatDate);
                String strDate = dateFormat.format(date);

                return strDate;
            }

            /**
             * Prints all details about the user in the console in one line
             *
             * @param dateFormat - in what format to display dates for example dd.mm.yyyy or
             *                   dd/mm/yyyy etc
             */
            public void printDetails(String dateFormat) {
                System.out.println("Name: " + firstName + " " + lastName + " | Age: " + age + " | Email:" + email
                        + " | Status: " + status + " | Registration Date: " + DateToString(registrationDate, dateFormat));
            }

            // Getters & Setters

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public Date getRegistrationDate() {
                return registrationDate;
            }

            public void setRegistrationDate(Date registrationDate) {
                this.registrationDate = registrationDate;
            }

            public Status getStatus() {
                return status;
            }

            public void setStatus(Status status) {
                this.status = status;
            }
        }

/**
 * User status list
 */
        enum Status {
            ACTIVE, INACTIVE, BLOCKED, NEW
        }
