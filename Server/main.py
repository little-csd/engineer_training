import websockets
import asyncio

def recv_user_msg(websocket):
    recv_text = websocket.recv()
    print("recv_text:", websocket.pong, recv_text)

async def run(websocket, path):
    while True:
        try:
            recv_user_msg(websocket)
        except Exception as e:
            print("Exception:", e)

asyncio.get_event_loop().run_until_complete(websockets.serve(run, "192.168.101.65", 3891))
asyncio.get_event_loop().run_forever()