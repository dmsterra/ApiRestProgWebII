package br.com.senac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senac.entity.Estudante;
import br.com.senac.repository.EstudanteRepository;

@Service
public class EstudanteService {

	@Autowired
	private EstudanteRepository estudanteRepo;
	
	
	public Estudante salvar(Estudante estudante) {
		return estudanteRepo.save(estudante);
	}
	
	
	public List<Estudante> buscarTodosEstudantes(){
		return estudanteRepo.findAll();
	}
	
	
	public Estudante getEstudanteByID(Integer id) {
		return estudanteRepo.findById(id).orElse(null);
	}
	
	public Boolean deleteEstudante(Integer id) {
		Estudante estudante = estudanteRepo.findById(id).orElse(null);
		if(estudante != null) {
			estudanteRepo.deleteById(id);
			return true;
		}else {
			return false;
		}
	}
	
	
	public Estudante updateEstudante(Integer id, Estudante estudante) {
		Estudante estudanteBD = estudanteRepo.findById(id).orElse(null);
		if(estudanteBD != null) {
			estudanteBD.setNome(estudante.getNome());
			estudanteBD.setEmail(estudante.getEmail());
			estudanteBD.setSobreNome(estudante.getSobreNome());
			return estudanteRepo.save(estudanteBD);
		}else {
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
