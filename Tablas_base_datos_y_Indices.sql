CREATE TABLE Categoria ( 
	id_categoria serial PRIMARY KEY,
	nombre_categoria VARCHAR);
	
CREATE TABLE Subcategoria ( 
	id_subcategoria serial PRIMARY KEY,
	nombre_subcategoria VARCHAR,
	id_categoria int not null,
	FOREIGN KEY (id_categoria) REFERENCES Categoria (id_categoria));


CREATE TABLE Producto ( 
	id_producto serial PRIMARY KEY,
	nombre_producto VARCHAR,
	precio_producto NUMERIC (10,2),
	descripcion_producto VARCHAR,
	cantidad_producto INT,
	id_subcategoria INT NOT NULL,
	FOREIGN KEY (id_subcategoria) REFERENCES Subcategoria (id_subcategoria));

CREATE TABLE Calificacion ( 
	id_calificacion serial PRIMARY KEY,
	valor_calificacion NUMERIC (2,1),
	fecha_calificacion DATE,
	id_producto INT NOT NULL,
	FOREIGN KEY (id_producto) REFERENCES Producto (id_producto));

CREATE TABLE Comentario ( 
	id_comentario serial PRIMARY KEY,
	contenido_comentario VARCHAR,
	fecha_comentario DATE,
	id_producto INT NOT NULL,
	FOREIGN KEY (id_producto) REFERENCES Producto (id_producto));
	
CREATE TABLE usuario (
	id_usuario Serial PRIMARY KEY,
	correo_usuario VARCHAR,
	nombre_usuario VARCHAR,
	contrasena_usuario VARCHAR,
	tipo_usuario VARCHAR);


CREATE TABLE Trans_Inventario ( 
	id_trans_inventario serial PRIMARY KEY,
	tipo_trans_inventario VARCHAR,
	fecha_trans_inventario DATE,
	cantidad_trans_inventario INT,
	descripcion_trans_inventario VARCHAR,
	id_producto INT NOT NULL,
	id_usuario INT NOT NULL,
	FOREIGN KEY (id_usuario) REFERENCES Usuario (id_usuario),
	FOREIGN KEY (id_producto) REFERENCES Producto (id_producto));

    -- Índice para búsquedas de texto en nombre_producto
CREATE INDEX idx_producto_nombre_trgm
ON producto USING gin (lower(nombre_producto) gin_trgm_ops);

-- Índice para búsquedas por categoría
CREATE INDEX idx_categoria_nombre
ON categoria (lower(nombre_categoria));

-- Índice para búsquedas por rango de precios
CREATE INDEX idx_producto_precio
ON producto (precio_producto);

CREATE INDEX idx_producto_categoria_precio
ON producto (id_subcategoria, precio_producto);
