const mongoose = require("mongoose");

async function connect() {
  try {
    await mongoose.connect("mongodb://localhost:27017/Discord_Bot", {
      useNewUrlParser: true,
      useUnifiedTopology: true,
    });
    console.log("connnect successful");
  } catch (error) {
    console.log("connect failed");
  }
}

module.exports = { connect };
