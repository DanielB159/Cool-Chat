# Advanced2NetaGalDaniel

This is the project repository for our Advanced Programming 2 project.

# Brief Explanation
This project is an implementation of a web based chat service.
We are able to communicate between clients both browser side and Android application side abck and forth between the same server.
The server supports live changes between client to client, using socket.io for browser, and firebase for Android application.

# Running the server
In order to run the applicaiton, in the web or in the android, you must first run the server.
first clone the repository into a file and open the terminal in the file path.
next, run the command `npm i` to install all of the dependencies that the project uses.
finally, run `npm start` to run the application in the default web browser.
this command will run the server.

# Android app
In order to run the app, after cloning the repository, open android studio in the path `android/Cool_Chat_Supreme`
Inside android studio, run the app in a phone emiulator or a mobile phone.
When running the app, the app will first ask you to turn on notifications for it. Afterwards you will be prsented with a login screen.
In order to ensure or change to a correct URL server connection, you can click the settings icon (Cogwheel in the top) to change the URL. 
The default URL is http://10.0.2.2:5000/api - the url of the computer that emulates the phone (if android studio emulator is used)
if you want to use a normal phone, be sure to connect the computer and the phone to the same wifi network, and change the url of the app 
to the correct IPV4 adress of the computer in port 5000. 
for example: If the computer IPV4 ip is 50.6.7.8, change the URL to be: `http://50.6.7.8:5000/api`

Next, you would need to register, the application uses room for local storage, and mongodb for server side storage.
Register a new user, and log in through the login page.
At which point a new session will begin and you would be able to chat with all your mates and buddies and casual sexual partners and/or drug dealers.

### Adding a contact
In the contacts page, you are able to create new contacts using a conveniently placed button on the top right of the screen (which is also where the log out button stands).
### Removing a contact
In order to delete a chat/contact, you must *hold* over the contact for a short period of time.

# Browser client
The chat client will be found at "http://localhost:5000/".
Surfing to that url will load the home page of the app (login page).

### Page details
The main page is the login page, at path: '/'. 
In order to sign up you can press the referral link in the login page

If the login details are correct, you will be referred to the Chats page. There you can add new contacts by their Identifiers.
The new contacts will appear in the side of the chat-Box. After adding a new contact, you are able to start messaging them.

## Note
surfing to "http://localhost:5000/register" or "http://localhost:5000/chat" will not deliver the react files.
You must go through "http://localhost:5000/" to begin interaction with the server through our client.

# Server details
The server has been implemented according to the given example files.
The chat allows for all reasonable functionality, as well as creating multiple chats between the same two users, which is a very strange requirement indeed, Hemi.
