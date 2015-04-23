

var wx={
        getLocation:function(a){
            websiteReq('getLocation',
                            ParseObject(a),
                             a);
        },
        alert:function(a){
            websiteReq('alert',
                            ParseObject(a),
                             a);
        },
        toast:function(a){
            websiteReq('toast',
                            ParseObject(a),
                             a);
        },
        chooseImage:function(a){
            websiteReq('chooseImage',
                            ParseObject(a),
                             a);
        },
         previewImage:function(a){
            websiteReq('previewImage',
                            ParseObject(a),
                             a);
        },
        test:function(b){
            websiteReq('test',ParseObject(b),b);
        }
};

function websiteReq(interfaceNm,parameter,a){
     WxJSBridge.websiteReq(interfaceNm,parameter,function(result){clientReturn(interfaceNm,result,a);})
}
function clientReturn(interfaceNm,result,a){
   var jo=toJson(result);
   var responseStatus=(jo.errMsg).substring(interfaceNm.length+1);
    switch(responseStatus){
        case "ok":
            a.success&&a.success(jo);
            break;
        case "cancel":
             a.cancel&&a.cancel(jo);
            break;
        default:a.fail&&a.fail(jo)
    };
}

function toJson(obj){
    if(typeof(obj)=='string'){
        return JSON.parse( obj );
    }else{
        return obj;
    }
};
function ParseObject(obj){
        var strJson=Serialize(obj);
        console.log("BabJSBridge"+strJson);
      return toJson(strJson);
};
function Serialize(obj) {
	switch (obj.constructor) {
	case Object:
		var str = "{";
		for (var o in obj) {
		    if('success'==o||'fail'==o||'cancel'==o){
        		        continue;
            }
			str += '\"' + o + "\":\"" + Serialize(obj[o]) + "\","
		}
		if (str.substr(str.length - 1) == ",") str = str.substr(0, str.length - 1);
		return str + "}";
		break;
	case Array:
		var str = "[";
		for (var o in obj) {
			str += Serialize(obj[o]) + ","
		}
		if (str.substr(str.length - 1) == ",") str = str.substr(0, str.length - 1);
		return str + "]";
		break;
	case Boolean:
		return  obj.toString();
		break;
	case Date:
		return obj.toString();
		break;
	case Function:
		break;
	case Number:
		return obj.toString();
		break;
	case String:
		return obj.toString();
		break
	}
};

function empty(v){
    switch (typeof v){
        case 'undefined' : return true;
        case 'string' : if(trim(v).length == 0) return true; break;
        case 'boolean' : if(!v) return true; break;
        case 'number' : if(0 === v) return true; break;
        case 'object' :
        if(null === v) return true;
        if(undefined !== v.length && v.length==0) return true;
        for(var k in v){return false;} return true;
        break;
    }
    return false;
}