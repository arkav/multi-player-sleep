# Multi-Player Sleep
Single mixin mod, that allows only only one player to sleep inorder to skip the night. (Only needs to be loaded on the server).

## Configure
Sleep messages are configurable by editing the file `config/mpsleep.config.json`, sleep messages must include the players name as `%s`.

Example:
```json
{
  "messages": [
    "%s went to bed.",
    "Feeling tired? %s is."
  ]
}
```