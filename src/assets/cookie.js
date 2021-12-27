const Cookie =
    {

        set(cname, cvalue, exdays = 720) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + d.toUTCString();
            document.cookie = cname + "=" + cvalue + "; " + expires;
        },

        get(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1);
                if (c.indexOf(name) != -1) {
                    return c.substring(name.length, c.length);
                }
            }
            return "";
        },

        clear(cname) {
            var d = new Date();
            d.setTime(-1);
            var expires = "expires=" + d.toUTCString();
            document.cookie = cname + "=''; " + expires;
        },
    }
export default Cookie;