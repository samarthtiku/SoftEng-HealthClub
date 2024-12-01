# SoftEng-HealthClub
# 🏋️‍♂️ Gym Management System

Welcome to our SoftEng-HealthClub gym management system! 💪

## 🌟 Quick Start Guide

### 🔑 Sample Login Credentials

👤 Member:
- UserID: USER123
- Password: pass123
👤 Member:
- UserID: TEST123
- Password: test123
👤 User with NO membership:
- UserID: NOMEM123
- Password: NOMEM123

👨‍💼 Staff:
- UserID: STAFF123
- Password: staff123

👔 Management:
- UserID: MGMT123
- Password: mgmt123

### 🛠️ Technical Requirements
- ☕ Java JDK 17
- 💻 IntelliJ IDEA
- 🧪 JUnit 4.13.2

### 📦 Installation Steps
1. Clone the repo
2. Open in IntelliJ
3. Mark directories:
    - src/main/java as Sources Root
    - src/test/java as Test Sources Root

## 🎯 Features by User Type

### 🧑 Members Can:
- View account details
- Check membership status
- Track visit history

### 👨‍💼 Staff Can:
- Check member IDs
- Renew memberships
- Create memberships
- Generate reports

### 👔 Management Can:
- All staff capabilities +
- Edit user database
- System administration

## 📝 Format Guidelines

### 🆔 UserID Requirements:
- 6-10 characters
- Must include numbers
- Example: "USER123"

### 🎫 Membership Types:
- 3️⃣ THREE_MONTHS
- 6️⃣ SIX_MONTHS
- 1️⃣ ONE_YEAR
- 3️⃣ THREE_YEARS

## 🧪 Testing

We have 14 comprehensive test cases covering:
- ✅ User authentication
- ✅ Membership validation
- ✅ Visit tracking
- ✅ System integration

### Run Tests:
Navigate to src/test/java/com/gymmanager/test/
Run tests via IntelliJ's test runner

## 📂 Project Structure
```
src/
├── main/
│   └── java/
│       └── com/
│           └── gymmanager/
│               ├── 📱 enums/
│               │   └── Screen.java (Login/Home screen states)
│               │
│               ├── 📋 model/
│               │   ├── User.java (User authentication & roles)
│               │   ├── Membership.java (Membership management)
│               │   └── MemberAdmission.java (Visit tracking)
│               │
│               └── ⚙️ system/
│                   ├── GymSystem.java (Core system controller)
│                   └── Console.java (User interface handler)
│
└── test/
    └── java/
        └── com/
            └── gymmanager/
                └── test/
                    ├── GymSystemTest.java (System integration tests)
                    └── MembershipTest.java (Membership unit tests)
```

## 🤝 Support & Collaboration

Need help?
- 📧 Check test cases for examples
- 📚 Review format requirements
- 🔍 Verify credentials

## 🚀 Future Updates
- 📧 Email notifications
- 📊 Advanced reporting
- 📱 Mobile app integration
- 🎨 UI enhancements

---
Made with ❤️ by Elizabeth Bastow, Alicia Caruso, Ricardo Cordova, Andrew Rowe, Max Simmer, Samarth Tiku, Lee Wilson using IntelliJ on macOS

### Note:
Running smoothly? Give us a ⭐
Issues? Open a 🎫 ticket!
