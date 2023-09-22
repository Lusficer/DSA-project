require("dotenv").config();

const { Client, IntentsBitField } = require("discord.js");
const client = new Client({
  intents: [IntentsBitField.Flags.GuildEmojisAndStickers],
  intents: [IntentsBitField.Flags.GuildMessages],
  intents: [IntentsBitField.Flags.MessageContent],
  intents: [IntentsBitField.Flags.Guilds],
  // intents: [IntentsBitField.Flags.GuildMembers],
});

const startTheBot = () => {
  client.login(process.env.BOT_TOKEN);

  client.on("ready", (c) => {
    console.log(`${c.user.tag} is online`);
  });

  client.on("messageCreate", (msg) => {
    if (msg.content === "hey") {
      msg.reply("hey!!");
      console.log(`${msg.content}`);
    }
  });

  client.on("interactionCreate", async (interaction) => {
    if (!interaction.isChatInputCommand()) return;

    if (interaction.commandName === "ping") {
      await interaction.reply("Pong!");
    }
  });
};

module.exports = { startTheBot };
