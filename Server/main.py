import logging
from websocket_server import WebsocketServer

def new_client(client, server):
    print(client['id'])
    server.send_message_to_all("Hey, a new client come in")

def left_client(client, server):
    print('clien {} left'.format(client['id']))

def msg_recv(client, server, msg):
    print(msg)

server = WebsocketServer(13245, host='192.168.101.65')
server.set_fn_new_client(new_client)
server.set_fn_client_left(left_client)
server.set_fn_message_received(msg_recv)
server.run_forever()