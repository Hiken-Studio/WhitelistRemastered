# WhitelistRemastered
This plugin allows you to manage your whitelist in a completely new way.

## Main Command
The plugin main and only command is `/wr` which allows you to choose a subcommand from:

- `/wr add <playerName>` ➤ Adds a player to the whitelist
- `/wr rem <playerName>` ➤ Removes a player from the whitelist
- `/wr toggle` ➤ Enables/Disables the whitelist
- `/wr status` ➤ Shows you the status of the whitelist
- `/wr list` ➤ Shows you how many players are in the whitelist and their names
- `/wr reload` ➤ Reloads the configuration file

## Permissions
The plugin has only one permission which is `whitelistremastered.admin` and allwos you to access all the commnads above.

## Something good to know
By default, the whitelist is disabled and console can execute all of the commands above.
In order to run this plugin on your server, you **MUST** have a MySQL server running on your machine (or everywhere you want).
Head to configuration file and change the password from `whitelist_123` to your password otherwise the plugin won't start.
Make sure also to create the database first, I'll give you the command below
```sql
CREATE DATABASE WhitelistRemastered;
```
Once you've done all of these things, you will be able to run the plugin.

## Other things you don't want to read but are useful
If you find any bug in the plugin, please report it to me on Discord @ lory#6678
This plugin is offered by [Hiken Studio](https://discord.gg/Dth5SSMhRr)