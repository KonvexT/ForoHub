create table topicos (
    id bigint not null auto_increment,
    titulo varchar(200) not null,
    mensaje text not null,
    fecha_creacion datetime not null,
    status varchar(20) not null,
    autor_id bigint not null,
    curso_id bigint not null,
    primary key(id),
    constraint fk_topicos_autor foreign key (autor_id) references autores(id),
    constraint fk_topicos_curso foreign key (curso_id) references cursos(id)
);
