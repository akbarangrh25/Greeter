# 💬 Greeter Plugin

A Minecraft 1.21+ Paper plugin that sends **fake welcome messages** to new players. The twist? Only the new player can see them — and they're randomly "sent" by 25% of online players.

---

## ✨ Features

- 🎭 Fake welcome messages only visible to joining players
- 🎨 Supports [MiniMessage](https://docs.advntr.dev/minimessage/) formatting
- 🎯 Configurable delay, message pool, and greeting style
- 🔄 Live config reload with `/greeter reload`

---

## 📦 Installation

1. Download the latest `.jar` from [Releases](https://github.com/akbarangrh25/Greeter/releases).
2. Place it in your server's `plugins/` folder.
3. Start or reload your server.

---

## ⚙️ Configuration (`config.yml`)

```yaml
delay-start: 3
delay-min: 3
delay-max: 10

greetings:
  - "<gray><%greeter%></gray> <white>Welcome, <green>%new_player%</green>!</white>"
  - "<blue><%greeter%></blue> <gray>Glad you're here, <green>%new_player%</green>!</gray>"
````

---

## 🛠 Commands & Permissions

| Command           | Description        | Permission       |
| ----------------- | ------------------ | ---------------- |
| `/greeter reload` | Reload config live | `greeter.reload` |

---

## 🧪 Compatibility

* ✅ Minecraft 1.21 – 1.21.5+
* ✅ Java 21
* ✅ Requires Paper (for MiniMessage support)
