import net from 'net'
import readline from 'readline'
import { toUTF8Array } from './utils.js'
import { unpublishAll } from './announcement.js'


const consoleManager = readline.createInterface({
    input: process.stdin,
    output: process.stdout
})

let clientSocket;

export function openSocket(host, port, clientName) {
    // TODO: Refactor to connect to every user except the localhost one
    //  tip: maybe pass some property from NSD discovered service to see if it's localhost
    clientSocket = net.connect(port, host)
        .on('connect', () => { onConnected(clientName) })
        .on('error', (error) => { onError(error, host) })
        .on('data', (data) => { onData(data) })
}

function onConnected(clientName) {
    console.log("Socket opened toward " + clientName)
    setTimeout(function () {
        askQuestions()
    }, 10000)
}



function askQuestions() {
    const question = `
        Choose action:
         1. Send message
         2. Unpublish annoucement
        `
    consoleManager.question(question, function (answer) {
        if (answer == 1) {
            sendMessage("dummy message")
        } else {
            unpublishAll()
        }
        askQuestions()
    })
}

export function sendMessage(messageContent) {
    let message = {
        content: messageContent,
        user: "fake user",
        time: Date.now()
    }
    message = JSON.stringify(message)
    message = toUTF8Array(message)
    console.log(message)
    clientSocket.write(new Uint8Array(message))
}

function onData(data) {
    console.log(data)
    sendMessage("Automatic reply")
}

function onError(error, host) {
    console.log("Error occured with client: " + host)
    console.log(error)
}
