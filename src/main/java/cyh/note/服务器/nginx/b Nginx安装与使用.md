# 安装
brew install nginx

# 启动
- 第一种  
  ```
    cd /usr/local/Cellar/nginx/1.19.5/bin  
    ./nginx
  ```
- 第二种  
  `/usr/local/Cellar/nginx/1.19.5/bin/nginx -c /usr/local/etc/nginx/nginx.conf`

# 关闭
  ```
    cd /usr/local/Cellar/nginx/1.19.5/bin  
    nginx -s stop  
  ```
# 修改端口号
  ```
    vi /usr/local/etc/nginx/nginx.conf

    修改 listen 后面的端口号，再重启服务即可
    server {
        listen  8090;
    }
  ```

