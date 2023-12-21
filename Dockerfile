FROM node:18.17.1-alpine AS build
WORKDIR /usr/src/app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

#STAGE 2
FROM nginx:1.17.1-alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build /usr/src/app/dist/know-your-teacher/browser /usr/share/nginx/html
EXPOSE 80



#FROM node:latest as build
#WORKDIR /usr/local/app
#COPY package.json package-lock.json ./
#RUN npm install
#COPY . .
#RUN npm run build
#
#
#FROM nginx:latest
#COPY nginx.conf /etc/nginx/nginx.conf
#COPY --from=build /usr/src/app/dist/know-your-teacher /usr/share/nginx/html
#EXPOSE 80







#FROM node:18.17.1-alpine AS build
#
#WORKDIR /app
#COPY . .
#RUN npm install
#RUN npm run build
#
#FROM httpd:alpine3.19
#WORKDIR /usr/local/apache2/htdocs
#COPY --from=build /app/dist/know-your-teacher .
#FROM nginx:stable
#COPY --from=build /app/dist/know-your-teacher /usr/share/nginx/html
#EXPOSE 80


#FROM node:18-alpine as build
#doc
#WORKDIR /app
#COPY . .
#RUN npm install
#RUN npm run build
#
#FROM nginx:stable
#COPY --from=build /app/dist/know-your-teacher/ /usr/share/nginx/html
#EXPOSE 79
