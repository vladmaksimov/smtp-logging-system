'use strict';

const path = require('path'),
    webpack = require('webpack'),
    HtmlWebpackPlugin = require('html-webpack-plugin');

const output = path.join(__dirname, 'target/static');
const DEBUG = process.env.NODE_ENV !== 'production';

module.exports = {
    entry: {
        'angularjs-app': './src/main/app.js',
    },

    output: {
        path: output,
        filename: './[name].bundle.js'
    },

    devtool: 'eval',

    devServer: {
        proxy: {
            '/api/*': {
                target: 'http://localhost:8086',
                changeOrigin: true,
            }
        }
    },

    module: {
        loaders: [
            {
                test: /\.css$/,
                loader: 'style!css'
            }, {
                test: /\.scss$/,
                loader: 'style!css!autoprefixer!sass'
            }, {
                test: /\.js$/,
                exclude: /(node_modules)/,
                loader: 'ng-annotate?add=true!babel?presets[]=es2015'
            },
            {
                test: /\.html$/,
                loader: 'raw'
            },
            {
                test: /\.(png|jpe?g|gif|svg|woff|woff2|ttf|eot|ico)$/,
                loader: 'file?name=[name].[hash].[ext]'
            }
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({
            template: './src/main/index.html',
            chunks: ['angularjs-app'],
            inject: 'body',
            NODE_ENV: JSON.stringify(DEBUG ? 'development' : 'production'),
            'process.env': {
                NODE_ENV: JSON.stringify(DEBUG ? 'development' : 'production')
            }
        }),

        new webpack.DefinePlugin({
            NODE_ENV: JSON.stringify(DEBUG ? 'development' : 'production'),
            'process.env': {
                NODE_ENV: JSON.stringify(DEBUG ? 'development' : 'production')
            }
        }),

        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery',
            'window.jQuery': 'jquery'
        })
    ]
};

