$(() => {

    let stompClient = null;

    function updateView(isConnected) {
        $('#username').prop('disabled', isConnected);
        $('#connectBtn').prop('disabled', isConnected);
        $('#disconnectBtn').prop('disabled', !isConnected);
        $('#sendBtn').prop('disabled', !isConnected);
        $('#message').prop('disabled', !isConnected);
        $('#recipient').prop('disabled', !isConnected);
        if (isConnected) {
            $('#messages').text('');
        }
    }

    function getUsername() {
        return $('#username').val();
    }

    function onConnected() {
        updateView(true);
        stompClient.subscribe('/main-room', onMessage)
        stompClient.subscribe('/private-room/' + getUsername(), onMessage)
    }

    function onConnect() {
        const username = getUsername();
        const socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({username}, onConnected)
    }

    function onMessage(message) {
        const chatMessageDto = JSON.parse(message.body);
        $(`<p>${chatMessageDto.timestamp} ${chatMessageDto.sender}: ${chatMessageDto.text}</p>`)
            .appendTo($('#messages'));
    }

    function onSend() {
        const recipients = $('#recipient').val().split(',')
            .map((recipient) => recipient.trim())
            .filter((recipient) => recipient.length > 0);
        const chatMessageDto = {
            sender: getUsername(),
            recipients,
            text: $('#message').val()
        };
        stompClient.send('/ws/chat', {}, JSON.stringify(chatMessageDto));
    }

    function onDisconnect() {
        stompClient.disconnect();
        updateView(false);
    }

    updateView(false);
    $('#connectBtn').click(onConnect);
    $('#sendBtn').click(onSend);
    $('#disconnectBtn').click(onDisconnect);
});
