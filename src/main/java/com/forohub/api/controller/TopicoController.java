package com.forohub.api.controller;

import com.forohub.api.domain.autor.AutorRepository;
import com.forohub.api.domain.curso.CursoRepository;
import com.forohub.api.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriBuilder) {
        var autor = autorRepository.getReferenceById(datos.autorId());
        var curso = cursoRepository.getReferenceById(datos.cursoId());

        var topico = new Topico(datos, autor, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listar(
            @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        var page = topicoRepository.findAllByStatus(StatusTopico.ACTIVO, paginacion)
                .map(DatosListaTopico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizacionTopico datos) {
        var topico = topicoRepository.getReferenceById(datos.id());

        var autor = datos.autorId() != null ? autorRepository.getReferenceById(datos.autorId()) : null;
        var curso = datos.cursoId() != null ? cursoRepository.getReferenceById(datos.cursoId()) : null;

        topico.actualizarInformaciones(datos, autor, curso);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        var topico = topicoRepository.getReferenceById(id);
        topico.desactivar();
        return ResponseEntity.noContent().build();
    }
}
