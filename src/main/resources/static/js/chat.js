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
        client.connect({}, onConnect);
    };

    const onConnect = () => {
        updateView(true);
    }

    const onTimeUpdated = (socketMessage) => {

    };

    const onUserListUpdated = (socketMessage) => {

    };

    const changeStatus = () => {
        let isVisible = true;
        if(isVisibleBtn.is(':checked')) {
            isVisible = false;
        }
        let isBusy = false;
        if(isBusyBtn.is(':checked')) {
            isBusy = true;
        }

    };

    const onMessage = (chatMessage) => {

    };

    const disconnect = () => {
        updateView(false);
        client.disconnect();
    }

    const send = () => {

    };

    updateView(false);
    connectBtn.on('click', connect);
    disconnectBtn.on('click', disconnect);
    sendBtn.on('click', send);
    isVisibleBtn.change(changeStatus);
    isBusyBtn.change(changeStatus);

});