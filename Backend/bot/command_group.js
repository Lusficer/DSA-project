require("dotenv").config();
const { REST, Routes } = require("discord.js");

const commands = [
  {
    name: "hey",
    description: "Replies with hey!!",
  },
];

const rest = new REST({}).setToken(process.env.BOT_TOKEN);

(async ()   => {
    try {
        await rest.put(
            Routes.applicationGuildCommand(process.env.CLIENT_ID, process.env.GUILD_ID),
            {body: commands}
        )
        
    } catch (error) {
        console.log(`there was an error: ${error}`);
    }
})