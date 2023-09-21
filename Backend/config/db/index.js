const mongoose = require("mongoose");

async function connect() {
  try {
    await mongoose.connect(
      "mongodb+srv://Hoang145:Anhongdo20@cluster0.lwrnk8h.mongodb.net/DiscordBot",
      {
        useNewUrlParser: true,
        useUnifiedTopology: true,
      }
    );
    console.log("connnect successful");
  } catch (error) {
    console.log("connect failed");
  }
}

module.exports = { connect };
