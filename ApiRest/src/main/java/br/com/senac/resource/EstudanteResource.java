package br.com.senac.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.senac.dto.EstudanteDTO;
import br.com.senac.entity.Estudante;
import br.com.senac.service.EstudanteService;

@RestController
@RequestMapping("estudantes")
public class EstudanteResource {
	
	@Autowired
	private EstudanteService estudanteService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public ResponseEntity<List<EstudanteDTO>> buscarTodosEstudantes() {
		
		//Buscando estudantes
		List<Estudante> listaEstudantes = estudanteService.buscarTodosEstudantes();
		//Realizando o processo de conversão de cada estudante para DTO
		List<EstudanteDTO> listaEstudantesDTO = listaEstudantes.stream().map(
				estudante -> mapper.map(estudante, EstudanteDTO.class)).collect(Collectors.toList());
		
		return ResponseEntity.ok(listaEstudantesDTO);
	}
	
	@PostMapping
	public ResponseEntity<EstudanteDTO> cadastrarEstudante(@RequestBody EstudanteDTO estudanteDTO) {
		
		//Convertendo e colocando as informações em estudante
		Estudante estudante = mapper.map(estudanteDTO, Estudante.class);
		//Salvando Estudante e settando o ID(No POST o ID não é setado pelo usuário, então deve passar por essa conversão para o back-end settar)
		estudante = estudanteService.salvar(estudante);
		//Enviando novo Estudante pela API, reconvertendo em DTO(Agora o ID está grafado).
		EstudanteDTO novoEstudante = mapper.map(estudante, EstudanteDTO.class);
		
		return ResponseEntity.ok().body(novoEstudante);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EstudanteDTO> buscarEstudantePeloId(@PathVariable("id") Integer id){
		
		//Buscando do banco a informação
		Estudante estudante = estudanteService.getEstudanteByID(id);
		//Convertendo para DTO
		EstudanteDTO estudanteDTO = mapper.map(estudante, EstudanteDTO.class);
		//Retornando o DTO
		return ResponseEntity.ok().body(estudanteDTO);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EstudanteDTO> atualizarEstudante(@PathVariable("id") Integer id,@RequestBody EstudanteDTO estudanteDTO){
		
		Estudante estudante = mapper.map(estudanteDTO, Estudante.class);
		estudante = estudanteService.updateEstudante(id, estudante);
		
		EstudanteDTO atualizadoEstudante = mapper.map(estudante, EstudanteDTO.class);
		
		return ResponseEntity.ok().body(atualizadoEstudante);
	}
}
