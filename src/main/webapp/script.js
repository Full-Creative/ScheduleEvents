var add=(x,y)=>{
	console.log(x+y);
}

var add=(x,y)=>{
	console.log(x+y);
}
add(2,3);
//5
var add=(x,x)=>{
	console.log(x+x);
}
// Uncaught SyntaxError: Duplicate parameter name not allowed in this context

function add(x,x){console.log(x+x);}
add(4,6);
//12
new add(3,4);
//8

function mul(x,y){console.log(x*y);}
mul(2,3);
//6

new mul(10,10);
// 100

var mul=(x,y)=>{console.log(x*y);}
mul(2,3);
// 6
new mul(10,10);
// Uncaught TypeError: mul is not a constructor  at <anonymous>:1:1
