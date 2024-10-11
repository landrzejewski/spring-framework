$(() => {

    const clientId = crypto.randomUUID();
    const messages = $('#messages');
    const username = $('#username');
    const recipients = $('#recipients');
    const message = $('#message');
    const connectBtn = $('#connectBtn');
    const disconnectBtn = $('#disconnectBtn');
    const sendBtn = $('#sendBtn');

    let client = null;

    const updateView = (isConnected) => {
        username.prop('disabled', isConnected);
        recipients.prop('disabled', !isConnected);
        message.prop('disabled', !isConnected);
        if (isConnected) {
            messages.text('');
        }
        connectBtn.prop('disabled', isConnected);
        disconnectBtn.prop('disabled', !isConnected);
        sendBtn.prop('disabled', !isConnected);
    };

    const connect = () => {
        const socket = SockJS("/chat");
        client = Stomp.over(socket);
        client.connect({username: username.val(), clientId}, onConnected);
    };

    const onConnected = () => {
        updateView(true);
        client.subscribe('/main', onMessage);
    };

    const disconnect = () => {
        client.disconnect();
        updateView(false);
    };

    const send = () => {
        const messageDto = {
            sender: username.val(),
            recipients: [],
            text: message.val()
        };
        client.send('/ws/chat', {}, JSON.stringify(messageDto));
        message.val('');
    };

    const onMessage = (socketMessage) => {
        const messageDto = JSON.parse(socketMessage.body);
        const timestamp = new Date(messageDto.timestamp).toLocaleTimeString();
        $(`<p>${timestamp} ${messageDto.sender}: ${messageDto.text}</p>`).appendTo(messages);
    };

    updateView(false);
    connectBtn.click(connect);
    disconnectBtn.click(disconnect);
    sendBtn.click(send);

});