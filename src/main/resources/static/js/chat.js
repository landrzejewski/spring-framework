$(() => {

    const clientId = crypto.randomUUID();
    const privateClientId = crypto.randomUUID();
    const messages = $('#messages');
    const username = $('#username');
    const recipients = $('#recipients');
    const message = $('#message');
    const connectBtn = $('#connectBtn');
    const disconnectBtn = $('#disconnectBtn');
    const isVisibleBtn = $('#isVisibleBtn');
    const sendBtn = $('#sendBtn');
    const disabledProperty = 'disabled';

    let client = null;

    const updateView = (isConnected) => {
        username.prop(disabledProperty, isConnected);
        recipients.prop(disabledProperty, !isConnected);
        message.prop(disabledProperty, !isConnected);
        if (isConnected) {
            messages.text('');
        }
        connectBtn.prop(disabledProperty, isConnected);
        disconnectBtn.prop(disabledProperty, !isConnected);
        sendBtn.prop(disabledProperty, !isConnected);
    };

    const connect = () => {
        const socket = new WebSocket('/chat'); // SockJS("/chat");
        client = Stomp.over(socket);
        client.connect({username: username.val(), clientId, privateClientId}, onConnected);
    };

    const onConnected = () => {
        updateView(true);
        client.subscribe('/main', onMessage);
        client.subscribe('/private-' + privateClientId, onMessage);
        client.subscribe('/user-list', onUserListUpdated);
    };

    const disconnect = () => {
        client.disconnect();
        updateView(false);
    };

    const send = () => {
        const text = message.val();
        if (text) {
            const messageDto = {
                sender: username.val(),
                recipients: recipients.val(),
                text
            };
            client.send('/ws/chat', {}, JSON.stringify(messageDto));
            message.text('');
        }
    };

    const onMessage = (socketMessage) => {
        const messageDto = JSON.parse(socketMessage.body);
        const timestamp = new Date(messageDto.timestamp).toLocaleTimeString();
        $(`<p>${timestamp} ${messageDto.sender}: ${messageDto.text}</p>`).appendTo(messages);
    };

    const onUserListUpdated = (socketMessage) => {
        const usersDto = JSON.parse(socketMessage.body);
        recipients.empty();
        usersDto
            .filter(user => user.clientId !== clientId)
            .forEach(user => $(`<option value="${user.clientId}">${user.username} (${user.clientId})</option>`).appendTo(recipients));
    };

    function changeVisibility() {
        if($(this).is(':checked')) {
            console.log("1");
        } else {
            console.log("0");
        }
    }

    updateView(false);
    connectBtn.click(connect);
    disconnectBtn.click(disconnect);
    sendBtn.click(send);
    isVisibleBtn.change(changeVisibility);

});