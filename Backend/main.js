const express = require("express");
const morgan = require("morgan");
const hbs = require("express-handlebars");
const path = require("path");
const app = express();
const port = 3000;
const db = require("./config/db");
// const route = require("./routes");

app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use(express.static(path.join(__dirname, "public")));
app.use(morgan("combined"));
app.engine("hbs", hbs.engine({ extname: ".hbs" }));
app.set("view engine", "hbs");
app.set("view", path.join(__dirname, "resources/views"));

app.get("/", (req, res) => {
  res.send("Hello");
});

db.connect();
// route(app);

app.listen(port, () =>
  console.log(`Discord bot listening on port at http://localhost:${port}`)
);
