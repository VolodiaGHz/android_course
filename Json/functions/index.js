const functions = require('firebase-functions');
const jsonData = [
{
	"name":"Андрій",
	"car model":"мазда",
	"raiting":"4.9",
	"status":"В дорозі"
},
{
	"name":"Валерій",
	"car model":"ауді",
	"raiting":"4.9",
	"status":"Вільно"
},
{
	"name":"Ігор",
	"car model":"шевроле",
	"raiting":"4.9",
	"status":"В дорозі"
},
{
	"name":"Павло",
	"car model":"КІА",
	"raiting":"4.9",
	"status":"В дорозі"
},
{
	"name":"Михайло",
	"car model":"тойота",
	"raiting":"4.3",
	"status":"Вільно"
},
{
	"name":"Генадій",
	"car model":"бмв",
	"raiting":"4.6",
	"status":"Вільно"
},
{
	"name":"Іван",
	"car model":"мерседес",
	"raiting":"5.0",
	"status":"В дорозі"
},
{
	"name":"Сергій",
	"car model":"лада",
	"raiting":"4.9",
	"status":"Вільно"
},
{
	"name":"Олег",
	"car model":"черрі",
	"raiting":"4.5",
	"status":"В дорозі"
},
];

exports.cars = functions.https.onRequest((request, response) => {
 response.send(jsonData);
});