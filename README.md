<img align="right" height="128" width="128" alt="" loading="lazy" decoding="async" src="./src/main/resources/icon.png"/>

# Proximity Chat

Communicate with other players within a certain radius. Talking to your friends in private, or roleplaying with ease.

> **Important**
> - This is a **server-side mod**. You must install it on the server.
> - Required [Babric](https://github.com/Turnip-Labs/babric-instance-repo/releases) to run the mod.
> - Required [HalpLibe](https://github.com/Turnip-Labs/bta-halplibe/releases) in BTA version 7.1 and later.

## Features

- Select your channel with `/channel global` or `/channel prox`.
- Change the radius of your proximity chat with `/radius <radius>` (Eg: `/radius 50`).
- Quickly send a proximity message from the global channel (and vice versa!) by adding `# ` (hash and space) to the
  beginning of your message. Eg: `# Is anyone around me?`.
- Server operators can change the default (`default-proximity-radius=50`) and maximum proximity
  radii (`max-proximity-radius=50`) in the `server.properties` file.
