/**
 * *UTILS CORE
 * @author Francisco Parrinha
 */


const SLASH = '/';
const DEFAULT_URL = 'http://localhost:8081';
const NO_CONNECTION_TO_SERVER_MSG = "Could not connect to the Tasker server";


/**
 * A REST POST request to the TodoList server
 * @param url request url
 * @param body body as JSON object
 */
async function PostRequest(url : string, body : object) {
    let response = await fetch(`${DEFAULT_URL}${attachSlash(url)}`, {
        method: 'POST',
        mode: 'cors',
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json',
        }
    });
    

    return response;
}

/**
 * A REST PUT request to the TodoList server
 * @param url request url
 * @param body body as JSON object
 */
async function PutRequest(url : string, body : object) {
    let response = await fetch(`${DEFAULT_URL}${attachSlash(url)}`, {
        method: 'PUT',
        mode: 'cors',
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json',
        }
    });

    return response;
}

/**
 * A REST GET request to the TodoList server
 * @param url request url
 */
async function GetRequest(url : string) {
    let response = await fetch(`${DEFAULT_URL}${attachSlash(url)}`, {
        method: 'GET',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
        }
    });
    
    return response;
}

/**
 * A REST DELETE request to the TodoList server
 * @param url request url
 */
async function DeleteRequest(url : string) {
    let response = await fetch(`${DEFAULT_URL}${attachSlash(url)}`, {
        method: 'DELETE',
        mode: 'cors',
        headers: {
            'Content-Type': 'application/json',
        }
    });

    return response;
}

/**
 * Attaches a slash to the begin of the url if does not have it
 * @param url url to attach a slash to
 * @returns url with a slash at the beggining
 */
const attachSlash = (url : string) => {
    let isFirstCharSlash = url[0] === SLASH;

    if (isFirstCharSlash) {
        return url;
    }

    return `${SLASH}${url}`;
}

export { NO_CONNECTION_TO_SERVER_MSG, PostRequest, GetRequest, DeleteRequest, PutRequest }