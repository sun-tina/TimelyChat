const signupPage = document.querySelector("#signup-page");
const usernamePage = document.querySelector("#username-page");
const chatPage = document.querySelector("#chat-page");
const signupForm = document.querySelector("#signupForm");
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector("#messageForm")
const messageInput = document.querySelector("#message");
const messageArea = document.querySelector("#messageArea");
const connectingElement = document.querySelector(".connecting");
const userInput = document.querySelector(".form-control");

let stompClient = {};
let username = null;

// const enterButton = document.getElementById("enter")
signupForm.addEventListener("submit", connect);
usernameForm.addEventListener("submit", connect);
messageForm.addEventListener("submit",sendMessage);

document.getElementById('login').onclick = function(event) {
    usernamePage.classList.remove('hidden');
    chatPage.classList.add('hidden');
    signupPage.classList.add('hidden')
    event.preventDefault();
    console.log("button clicked");
    
    // alert("button was clicked");
 };

 document.getElementById('logout').onclick = function(event) {
    usernamePage.classList.remove('hidden');
    chatPage.classList.add('hidden');
    signupPage.classList.add('hidden')
    stompClient.disconnect();
    userInput.value='';
    event.preventDefault();
    console.log("button clicked");
    
    // alert("button was clicked");
 };

 document.getElementById('sign-up').onclick = function(event) {
    signupPage.classList.remove('hidden')
    chatPage.classList.add('hidden');
    usernamePage.classList.add('hidden');
    userInput.value='';
    event.preventDefault();
    console.log("button clicked");
 };

// function backButton(){
//     chatPage.classList.add('hidden');
//     usernamePage.classList.remove('hidden');
// }

function connect(event){
    event.preventDefault();
    console.log("connected");
    try{
        loginUser = document.getElementById('user').value.trim();
        loginUserLength = document.getElementById('user').value.trim().length;
        signupUser = document.getElementById('username').value.trim();
        signupUserLength = document.getElementById('username').value.trim().length;
        if (loginUserLength > 0){
            username = loginUser;
        }else{
            username = signupUser;
        }
        console.log(username);
        if(username){
            console.log("username got");
            signupPage.classList.add('hidden');
            usernamePage.classList.add('hidden');
            chatPage.classList.remove('hidden');
            
            // const socket = new WebSocket('ws://localhost:8080/ws');
            var socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);

            stompClient.connect({}, onConnected, onError);

            // connectPromise.then(onConnected, function(error) {
            //   console.error("Connection error:", error);
            //   // Handle the connection error appropriately
            // });
        }else{
            console.log(username);
            console.error("username not found");
        }
    }catch(error){
        console.log(error);
    }
}

function onConnected(){
    stompClient.subscribe('/topic/public', onMessageRecieved);

    //username to server via chat controller
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender: username, type: 'JOIN'}));
    //once user is connected, hide the connecting message
    connectingElement.classList.add('hidden');
}

function onMessageRecieved(payload){
    //extract the body from payload (message)
    let message = JSON.parse(payload.body);
    //li for message list
    let messageElement = document.createElement('li');
    //check message type
    if(message.type === 'JOIN'){
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    }else if(message.type === 'LEAVE'){
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    }else{
        messageElement.classList.add('chat-message');
        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);

        messageElement.appendChild(avatarElement);

        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        
    }
    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    messageElement.appendChild(textElement);
    messageArea.appendChild(messageElement);
    //scroll to always show the latest message
    messageArea.scrollTop = messageArea.scrollHeight
}

function sendMessage(event){
    let messageContent= messageInput.value.trim();
    if(messageContent && stompClient){
        let chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send(
            //endpoint
            '/app/chat.sendMessage',
            {},
            JSON.stringify(chatMessage)
        );
        //empty message field
        messageInput.value = '';
    }
    event.preventDefault();

}

function onError(){
    connectingElement.textContent = "connection to WebSocket server failed.";
}

