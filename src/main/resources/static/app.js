var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    console.log("--->",connected)
    if (connected) {
        $("#conversation").show();
        $("#chat").show();
    } else {
        $("#conversation").hide();
        $("#chat").hide();
    }
    $("#greetings").html("");
}

//建立一个 WebSocket 连接，在建立 WebSocket 连接时，用户必须先输入用户名，然后才能建立连接
function connect() {
    if (!$("#name").val()) {
        return;
    }
    //使用 SockJS 建立连接，然后创建一个 STOMP 实例发起连接请求 在连接成功的回调方法中，
    // 首先调用 setConnected(true);方法进行页面的设置，然后调用 STOMP 中的 subscribe 方法订阅服务端发送回来的消息，并将服务端发送来的消息展示出来（使用 showGreeting 方法）
    var socket = new SockJS('/chitchat/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        stompClient.subscribe('/topic/greetings', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
    })
}

// 断开一个 WebSocket 连接
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
}

function sendName() {
    stompClient.send("/app/send", {},
        JSON.stringify({
            'name': $("#name").val(), 'content': $("#content").val()
        }));
}

function showGreeting(message) {
    console.log("message--->",message)
    $("#greetings").append("<div>" + message.name + ":" + message.content + "</div>");
}

$(function () {
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
})