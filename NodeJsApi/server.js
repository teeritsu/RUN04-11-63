////Update 5/10/63 project Apprunner
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
extended: true
}));

app.listen(3000, function () {
    console.log('Node app is running on port 3000');
});

module.exports = app;

// default route
app.get('/', function (req, res) {
    return res.send({ error: true, message: 'Test Web API' })
    });
    
    // connection configurations
    var dbConn = mysql.createConnection({
        host: 'localhost',
        user: 'root', 
        password: '',
        database: 'data_running'
    });

// add event
app.post('/add_event', function (req,res){
    console.log("Add event")
    var data = req.body
    if (!data ) {
         return res.status(400).send({ error:true, message: 'Please Register Again' });
    }

    dbConn.query(`INSERT INTO add_event (name_organizer,
        name_event,
        name_producer,
        date_reg_start,
        date_reg_end,
        objective,
        detail,
        pic_event) VALUES (?,?,?,?,?,?,?,?)`, [req.body.name_organizer,
        req.body.name_event,
        req.body.name_producer,
        req.body.date_reg_start,
        req.body.date_reg_end,
        req.body.objective,
        req.body.detail,
        req.body.pic_event], function (error, results, fields) {

        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json({"status":"ok"});
            res.end()
        }
    });
});
    
//login
app.get('/login/:email/:password', function (req, res){
    console.log("login")
    let email = req.params.email;
    let password = req.params.password;
    
    if (!email) {
        return res.status(400).send({ error: true, message: 'Please provide email'});
    }
    if (!password) {
        return res.status(400).send({ error: true, message: 'Please provide password'});
    }
        
        dbConn.query('SELECT * FROM user where email = ? AND password = ?', [email,password], function (error, results, fields) {
            if (error){
                console.log(error)
                res.status(422).json({"status":"failed"});
                res.end()
            }else{
                res.status(200).json(results[0]);
                res.end()
            }
        });

    });

//insert register
app.post('/register', function(req,res){
    console.log("register")
    var register = req.body

    if(!register){
        return res.status(400).send({error:true,message: 'Please provide student'});
    }
    dbConn.query("INSERT INTO user SET ?", register , function(error, results, fieldds){
        if (error) throw error;
        return res.send(results);
    });
});

//insert deail_distance
app.post('/detail_distance', function(req,res){
    console.log("detail_distance")
    var register = req.body

    if(!register){
        return res.status(400).send({error:true,message: 'Please provide student'});
    }
    dbConn.query("INSERT INTO detail_distance SET ?", data , function(error, results, fieldds){
        if (error) throw error;
        return res.send(results);
    });
});

//show_event
app.get('/show_event', function (req, res) {
    dbConn.query('SELECT * FROM add_event where type = 1', function (error, results, fields) {
        console.log("show_event")
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});

//search
app.get('/search_event/:status/:name_event', function (req, res){
    console.log("search")
    let name_event = req.params.name_event;
    let status = req.params.status;

    if (!name_event) {
        return res.status(400).send({ error: true, message: 'Please provide name_event'});
    }
    
    dbConn.query("SELECT * FROM add_event where name_event LIKE '%' ? '%' AND type = 1", [name_event,status], function (error, results, fields) {
        if (error){
            console.log(error)
            res.status(422).json({"status":"failed"});
            res.end()
        }else{
            res.status(200).json(results);
            res.end()
        }
    });
});
