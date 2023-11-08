<img align="right" height="128" width="128" alt="" loading="lazy" decoding="async" src="./src/main/resources/icon.png"/>

# Sort Chest

Organize your chests instantly with a single keystroke

> **Important**
> Required [Babric](https://github.com/Turnip-Labs/babric-instance-repo/releases) to run the mod.

## How to use

1. Open a chest
2. Press a key:
    - `S`: Sort
    - `F`: Fill (inventory → chest)
    - `D`: Dump (chest → inventory)

## FAQ

### Server?

**Yes**, you can use the sort chest mod on a server. However, the item might jump for a while after sorting because the mod will emulate mouse clicks rather than directly changing the inventory data.

### How does the sort chest mod sort items?

Items are sorted by their item ID (ascending), followed by its metadata (like wool color code, ascending), and finally by its stack size (descending).
