// boot
import {OpenAudioMc} from "../OpenAudioMc";
import ClientTokenSet from "./ClientTokenSet";

let openAudioMc = null;

export default openAudioMc;

function enable() {
    if (openAudioMc.canStart) {
        openAudioMc.start();
    }
}

export function linkBootListeners() {
    const isSafari = navigator.vendor && navigator.vendor.indexOf('Apple') > -1 &&
        navigator.userAgent &&
        navigator.userAgent.indexOf('CriOS') == -1 &&
        navigator.userAgent.indexOf('FxiOS') == -1;

    if (isSafari) {
        window.location.href = "https://mindgamesnl.github.io/OpenAudioMc/browsers.html";
        return;
    }

    let tokenSet = new ClientTokenSet().fromUrl(window.location.href);
    if (tokenSet == null) {
        document.getElementById('footer-welcome').innerText = 'No authentication provided';
        document.getElementById("boot-button").style.display = "none";
        document.getElementById("welcome-text-landing").innerHTML = "The audio client is only available for players who are online in the server. Use <small>/audio</small> to obtain a URL<br />";
        return;
    }

    document.body.onclick = () => enable();

    // can we find a name? let's put it as a welcome text!
    // makes the experiance a bit more personal

    if (tokenSet != null && tokenSet.name != null) {
        document.getElementById("sidebar-head").style.background = "linear-gradient(0deg, rgba(42, 38, 95, .8), rgba(42, 38, 95, .4)), url(https://minotar.net/avatar/" + tokenSet.name + ")";
        document.getElementById('footer-welcome').innerText = 'Logged in as ' + tokenSet.name;
        openAudioMc = new OpenAudioMc();
    }
}
