import * as path from 'path';
import * as express from 'express';
import { createProxyMiddleware } from 'http-proxy-middleware';

const app = express();

app.use(
  '/backend',
  createProxyMiddleware({
    target: 'http://localhost:8080',
    changeOrigin: true,
    pathRewrite: {
      '^/backend': '',
    },
  })
);

app.use(express.static('./dist/eventcenter-client'));

app.use('*', (req, res) => {
  res.sendFile(path.resolve('./dist/eventcenter-client/index.html'));
});

const PORT = process.env['PORT'] || 8080;
app.listen(PORT);