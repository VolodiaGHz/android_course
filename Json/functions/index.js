const functions = require('firebase-functions');
const jsonData = [
{
	"name":"Андрій",
	"car model":"мазда",
	"raiting":"4.9",
	"status":"В дорозі",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
{
	"name":"Валерій",
	"car model":"ауді",
	"raiting":"4.9",
	"status":"Вільно",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
{
	"name":"Ігор",
	"car model":"шевроле",
	"raiting":"4.9",
	"status":"В дорозі",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
{
	"name":"Павло",
	"car model":"КІА",
	"raiting":"4.9",
	"status":"В дорозі",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
{
	"name":"Михайло",
	"car model":"тойота",
	"raiting":"4.3",
	"status":"Вільно",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
{
	"name":"Генадій",
	"car model":"бмв",
	"raiting":"4.6",
	"status":"Вільно",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
{
	"name":"Іван",
	"car model":"мерседес",
	"raiting":"5.0",
	"status":"В дорозі",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
{
	"name":"Сергій",
	"car model":"лада",
	"raiting":"4.9",
	"status":"Вільно",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
{
	"name":"Олег",
	"car model":"черрі",
	"raiting":"4.5",
	"status":"В дорозі",
	"photo":"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvQ0TwODFoX9bnNN0aLRVal1_EPY39eq8trHaHgUBK43Ry78sP&s"
},
];

exports.cars = functions.https.onRequest((request, response) => {
 response.send(jsonData);
});