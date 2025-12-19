$(() => {

    const clientId = crypto.randomUUID();
    const privateClientId = crypto.randomUUID();
    const messages = $('#messages');
    const username = $('#username');
    const time = $('#time');
    const recipients = $('#recipients');
    const message = $('#message');
    const connectBtn = $('#connectBtn');
    const disconnectBtn = $('#disconnectBtn');
    const isVisibleBtn = $('#isVisibleBtn');
    const isBusyBtn = $('#isBusyBtn');
    const sendBtn = $('#sendBtn');
    const disabledProperty = 'disabled';

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
        isVisibleBtn.prop(disabledProperty, !isConnected);
        isBusyBtn.prop(disabledProperty, !isConnected);
    };

    let client = null;

    const connect = () => {
        if (!username.val()) {
            return;
        }
        const socket = new WebSocket('/chat');
        client = Stomp.over(socket);
        client.connect({username: username.val(), clientId, privateClientId}, onConnect);
    };

    const onConnect = () => {
        updateView(true);
        client.subscribe('/main', onMessage);
        client.subscribe('/private-' + privateClientId, onMessage);
        client.subscribe('/user-list', onUserListUpdated);
        client.subscribe('/time', onTimeUpdated);
        changeStatus();
    }

    const onTimeUpdated = (socketMessage) => {
        const message = JSON.parse(socketMessage.body);
        const timestamp = new Date(message.timestamp).toLocaleTimeString();
        time.text('Server time: ' + timestamp);
    };

    const onUserListUpdated = (socketMessage) => {
        const users = JSON.parse(socketMessage.body);
        recipients.empty();
        users
            .filter(user => user.clientId !== clientId)
            .forEach(user=> $(`<option value="${user.clientId}">${user.username} (${user.clientId}) ${user.status.isBusy ? '- busy' : ''}</option>`).appendTo(recipients));
    };

    const changeStatus = () => {
        client.send('/ws/update-status', {}, JSON.stringify({}));
    };

    const onMessage = (chatMessage) => {
        const message = JSON.parse(chatMessage.body);
        const timestamp = new Date(message.timestamp).toLocaleTimeString();
        $(`<p>${timestamp} ${message.sender}: ${message.text}</p>`).appendTo(messages);
    };

    const disconnect = () => {
        updateView(false);
        client.disconnect();
    }

    const send = () => {
        const text = message.val();
        if (text) {
            const messageDto = {
                sender: username.val(),
                recipients: recipients.val(),
                text,
            };
            client.send('/ws/chat', {}, JSON.stringify(messageDto));
            message.text('');
        }
    };

    updateView(false);
    connectBtn.on('click', connect);
    disconnectBtn.on('click', disconnect);
    sendBtn.on('click', send);
   /* isVisibleBtn.change(changeStatus);
    isBusyBtn.change(changeStatus);*/

});