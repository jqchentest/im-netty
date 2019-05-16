    var ws = {
        login: function(userId) {
            if (!window.WebSocket) {
                  return;
            }
            if (socket.readyState === WebSocket.OPEN) {
                var data = {
                    "userId" : userId,
                    "type" : "login"
                };
                socket.send(JSON.stringify(data));
            } else {
                alert("Websocket连接没有开启！");
            }
        },

        sendContent: function(userId, friendId, content) {
            if (!window.WebSocket) {
                  return;
            }
            if (socket.readyState === WebSocket.OPEN) {
                var data = {
                    "userId" : userId,
                    "friendId" : friendId,
                    "content" : content,
                    "type" : "send"
                };
                socket.send(JSON.stringify(data));
            } else {
                alert("Websocket连接没有开启！");
            }
        },

        logout: function(userId) {
            if (!window.WebSocket) {
                  return;
            }
            if (socket.readyState === WebSocket.OPEN) {
                var data = {
                    "userId" : userId,
                    "type" : "logout"
                };
                socket.send(JSON.stringify(data));
            } else {
                alert("Websocket连接没有开启！");
            }
        }
    };

