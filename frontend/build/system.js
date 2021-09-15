/* eslint-disable import/no-extraneous-dependencies */
/* eslint-disable import/no-dynamic-require */

const path = require('path');

const minimist = require('minimist');
const partial = require('lodash/partial');
const chalk = require('chalk');
const ora = require('ora');

const StyleguidistError = require('react-styleguidist/lib/scripts/utils/error');
const styleguidist = require('vue-styleguidist');

const { getRemoteConfig, cleanup: cleanupRemoteBuild } = require('./system.remote.utils');

const argv = minimist(process.argv.slice(2));
const command = argv._[0];

// This will be used if NODE_ENV isn't already set
if (!process.env.NODE_ENV) {
    process.env.NODE_ENV = command === 'build' ? 'production' : 'development';
}

let config = require(path.resolve(__dirname, './system.config'));

const spinner = ora('Building design system...');

// Do not show nasty stack traces for Styleguidist errors
process.on('uncaughtException', (err) => {
    if (err.code === 'EADDRINUSE') {
        console.log(
      `Another server is running at ${
        config ? `port ${ config.serverPort}` : 'the port'
      } already. Please stop it or change the default port to continue. You can change the port using the \`serverPort\` option in the styleguide config`
        );
    } else if (err instanceof StyleguidistError) {
        console.error(chalk.red(err.message));
    } else {
        console.error(err.toString());
        console.error(err.stack);
    }
    process.exit(1);
});

function callbackCommon(err, stats) {
    spinner.stop();
    if (err) throw err;

    if (stats) {
        process.stdout.write(
            `${stats.toString({
                colors: true,
                modules: false,
                children: false,
                chunks: false,
                chunkModules: false,
            }) }\n\n`
        );

        if (stats.hasErrors()) {
            console.log(chalk.red('Design System build failed with errors.\n'));
            process.exit(1);
        }
    }

    console.log(chalk.cyan('Design System build complete.\n'));
}

function buildCallback(err, inpConfig, stats) {
    callbackCommon(err, stats);

    cleanupRemoteBuild(inpConfig);
}

function serverCallback(err, inpConfig, stats) {
    callbackCommon(err, stats);

    const url = `http://${inpConfig.serverHost === '0.0.0.0' ? 'localhost' : inpConfig.serverHost}:${
    config.serverPort
  }`;

    console.log(chalk.yellow(inpConfig.title, `running at ${url}`));

    // can't cleanup until after the server has stopped
    // cleanupRemoteBuild(remoteConfig, inpConfig)
}

function run(inpCommand, returnedConfig) {
    config = returnedConfig;
    spinner.start();

    const styleguide = styleguidist(config);

    switch (inpCommand) {
    case 'server':
        styleguide.server(serverCallback);
        break;
    case 'build':
    default:
        styleguide.build(buildCallback);
    }
}

getRemoteConfig(config, argv)
    .then(partial(run, command))
    .catch((err) => {
        console.log(chalk.red('Problem getting remote config:', err));
    });
